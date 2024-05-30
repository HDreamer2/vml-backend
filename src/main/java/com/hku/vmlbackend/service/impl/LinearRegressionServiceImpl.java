package com.hku.vmlbackend.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hku.vmlbackend.dto.EpochDataDTO;
import com.hku.vmlbackend.dto.LinearRegressionTrainDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class LinearRegressionServiceImpl implements LinearRegressionService {
    @Value("${python.server.url}")
    private String pythonServerUrl;
    @Autowired
    private FileService fileService;
    @Autowired
    private SocketIOServer socketIOServer;
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void train(LinearRegressionTrainDTO dto) {
        File file = fileService.getFileByMD5(dto.getMd5());
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
                pythonServerUrl + "/linear-regression/train",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to train model: " + response.getStatusCode());
        }

    }
    @Override
    public void getEpochData(EpochDataDTO dto) {
        //TODO 通过socketio将数据传递给前端
        // 通过Socket.IO将数据传递给前端
        socketIOServer.getBroadcastOperations().sendEvent("epochData", dto);

        log.info("Epoch: {}, Weights: {}, Bias: {}, Loss: {}", dto.getEpoch(), dto.getWeights(), dto.getBias(), dto.getLoss());
    }
}
