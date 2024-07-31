package com.hku.vmlbackend.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hku.vmlbackend.common.ObjectMapperInstance;
import com.hku.vmlbackend.dto.LinearRegressionEpochDataDTO;
import com.hku.vmlbackend.dto.LinearRegressionTrainDTO;
import com.hku.vmlbackend.entity.LinearRegressionData;
import com.hku.vmlbackend.entity.RandomForestData;
import com.hku.vmlbackend.handler.NettySocketHandler;
import com.hku.vmlbackend.mapper.LinearRegressionDataMapper;
import com.hku.vmlbackend.mapper.RandomForestDataMapper;
import com.hku.vmlbackend.service.FileService;
import com.hku.vmlbackend.service.LinearRegressionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class LinearRegressionServiceImpl implements LinearRegressionService {
    @Value("${python.server.url}")
    private String pythonServerUrl;
    @Autowired
    private FileService fileService;
    @Autowired
    private SocketIOServer socketIOServer;
    // 用于存储待发送的数据，key 为 userId
    private final ConcurrentHashMap<String, StringBuilder> pendingData = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = ObjectMapperInstance.INSTANCE.getObjectMapper();

    @Autowired
    private LinearRegressionDataMapper linearRegressionDataMapper;

    @Override
    public void train(LinearRegressionTrainDTO dto) {
//        File file = fileService.getFileByMD5(dto.getMd5());
        File file = fileService.getFileByFileId(dto.getFileId());
        if (file == null) {
            throw new RuntimeException("File not found");
        }

        // 将文件传递给Python后端
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 创建MultiValueMap用于传递文件
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));
        try {
            body.add("features", objectMapper.writeValueAsString(dto.getFeatures()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        body.add("label", dto.getLabel());
        body.add("epoch", dto.getEpoch());
        body.add("userId",dto.getUserId());
        body.add("fileId",dto.getFileId());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                pythonServerUrl + "/linear-regression/train",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to train model: " + response.getStatusCode());
        }

    }

    public void getEpochData(LinearRegressionEpochDataDTO dto) {
        // 插入数据库
        LinearRegressionData linearRegressionData=new LinearRegressionData();
        linearRegressionData.setUserId(dto.getUserId());
        linearRegressionData.setFileId(dto.getFileId());
        linearRegressionData.setEpoch(dto.getEpoch());
        linearRegressionData.setLoss(dto.getLoss());
        linearRegressionData.setBias(dto.getBias().toString());
        linearRegressionData.setWeights(dto.getWeights().toString());

        linearRegressionDataMapper.insert(linearRegressionData);
        // 通过Socket.IO将数据传递给前端
        try {
            String epochDataJson = objectMapper.writeValueAsString(dto);
            // 获取所有连接的客户端
            Collection<SocketIOClient> allClients = socketIOServer.getAllClients();
            // 记录所有连接的客户端的 sessionId
            StringBuilder sessionIds = new StringBuilder();
            for (SocketIOClient client : allClients) {
                sessionIds.append(client.getSessionId().toString()).append(", ");
            }
            // 移除最后的逗号和空格
            if (sessionIds.length() > 0) {
                sessionIds.setLength(sessionIds.length() - 2);
            }
            String eventName = "epochData_" + dto.getUserId();

            // 发送事件
            socketIOServer.getBroadcastOperations().sendEvent(eventName, epochDataJson);

            // 记录日志，包含所有 sessionId
            log.info("Epoch data sent to clients. SessionIDs: [{}]. Data: {}", sessionIds, epochDataJson);
        } catch (JsonProcessingException e) {
            log.error("Error converting EpochDataDTO to JSON", e);
        }

    }
//    @Override
//    public void getEpochData(LinearRegressionEpochDataDTO dto) {
//        try {
//            String epochDataJson = objectMapper.writeValueAsString(dto);
//
////            String eventName = "epochData_" + dto.getUserId();
//            //TODO 获取socketio onConnect的clientId
//            String clientId = getClientIdByUserId(dto.getUserId());
//            log.info("clientId:{}",clientId);
//            if (clientId != null) {
////                sendPendingDataWhenClientIdAvailable(clientId);
//                // 发送事件
////                socketIOServer.getBroadcastOperations().sendEvent(eventName, epochDataJson);
//                sendEpochData(clientId, epochDataJson);
////
//            }else if (clientId == null){
//                // 如果 clientId 为 null，将数据添加到 pendingData 中
//                // 这里使用 StringBuilder 来累计数据，如果需要可以改为其他方式
//                pendingData.compute(dto.getUserId().toString(), (key, builder) -> {
//                    if (builder == null) {
//                        builder = new StringBuilder();
//                    }
//                    builder.append(epochDataJson).append(";"); // 使用分号作为简单分隔
//                    return builder;
//                });
//                log.info("ClientId is null for userId: {}. Data will be saved until clientId is available.pendingData is :{}", dto.getUserId(),pendingData.get(dto.getUserId().toString()));
//            }
//        } catch (JsonProcessingException e) {
//            log.error("Error sending EpochDataDTO to frontend", e);
//        }
//    }
//

    // 私有方法，用于发送数据
    private void sendEpochData(String clientId, String epochDataJson) {

        // 获取所有连接的客户端
        Collection<SocketIOClient> allClients = socketIOServer.getAllClients();
        // 记录所有连接的客户端的 sessionId
        StringBuilder sessionIds = new StringBuilder();
        for (SocketIOClient client : allClients) {
            sessionIds.append(client.getSessionId().toString()).append(", ");
        }
        // 移除最后的逗号和空格
        if (sessionIds.length() > 0) {
            sessionIds.setLength(sessionIds.length() - 2);
        }
        // 这里可以根据需要修改发送逻辑，例如发送给所有客户端或特定客户端
        // 以下为示例，发送给特定 clientId
        socketIOServer.getBroadcastOperations().sendEvent("epochData_" + clientId, epochDataJson);
        // 记录日志，包含所有 sessionId
        log.info("Epoch data sent to clientId:{}. SessionIDs: [{}]. Data: {}", clientId,sessionIds, epochDataJson);
    }
    // 该方法在 clientId 首次变为非空时被调用，发送所有待发送的数据
    public void sendPendingDataWhenClientIdAvailable(String clientId) {
        String userId =clientId; // 根据实际情况实现这个方法
        log.info("userId ={}",userId);
        if (userId != null) {
            StringBuilder pendingDataBuilder = pendingData.get(userId);
            if (pendingDataBuilder != null) {
                // 发送累计的数据
                String dataToSend = pendingDataBuilder.toString();
                sendEpochData(clientId, dataToSend);
                // 从 map 中移除已发送的数据
                pendingData.remove(userId);
                log.info("Sent pending epoch data to clientId: {}. Data: {}", clientId, dataToSend);
            }
        }
    }


    /**
     * 根据 userId 获取 clientId。
     * 这个方法需要在 NettySocketUtil 或其他合适的地方实现。
     * @param userId 用户 ID
     * @return 客户端 ID，如果未找到则返回 null
     */
    private String getClientIdByUserId(Integer userId) {
        // 遍历 clientMap 来找到匹配的 userId
        for (Map.Entry<String, SocketIOClient> entry : NettySocketHandler.clientMap.entrySet()) {
            // 这里假设每个客户端在连接时传递了 userId 作为 URL 参数 "userId"
            String clientId = entry.getKey();
//            SocketIOClient client = entry.getValue();
//            String clientUserId = client.getHandshakeData().getSingleUrlParam("userId");
            if (clientId != null && clientId.equals(userId.toString())) {
                return clientId;
            }
        }
        return null; // 如果没有找到匹配的 userId，返回 null
    }
    @Override
    public List<LinearRegressionData> getHttpData(Integer userId, String fileId) {
        return linearRegressionDataMapper.getByUserIdAndFileId(userId,fileId);
    }

}