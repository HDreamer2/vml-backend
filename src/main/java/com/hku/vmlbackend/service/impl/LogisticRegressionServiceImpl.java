package com.hku.vmlbackend.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hku.vmlbackend.common.ObjectMapperInstance;
import com.hku.vmlbackend.dto.LogisticRegressionEpochDataDTO;
import com.hku.vmlbackend.dto.LogisticRegressionTrainDTO;
import com.hku.vmlbackend.service.FileService;
import com.hku.vmlbackend.service.LogisticRegressionService;
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

@Slf4j
@Service
public class LogisticRegressionServiceImpl implements LogisticRegressionService {

    @Autowired
    private FileService fileService;
    @Value("${python.server.url}")
    private String pythonServerUrl;
    @Autowired
    private SocketIOServer socketIOServer;

    ObjectMapper objectMapper = ObjectMapperInstance.INSTANCE.getObjectMapper();

    @Override
    public void train(LogisticRegressionTrainDTO dto) {
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

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                pythonServerUrl + "/logistic-regression/train",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to train model: " + response.getStatusCode());
        }
    }

    @Override
    public void getEpochData(LogisticRegressionEpochDataDTO dto) {
        //TODO 通过socketio将数据传递给前端
        // 通过Socket.IO将数据传递给前端
        socketIOServer.getBroadcastOperations().sendEvent("epochData", dto);

        log.info("Epoch: {}, Weights: {}, Bias: {}, Loss: {}, Feature Weights: {}",
                dto.getEpoch(), dto.getWeights(), dto.getBias(), dto.getLoss(), dto.getFeatureWeights());
    }
}
