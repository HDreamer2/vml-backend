package com.hku.vmlbackend.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hku.vmlbackend.common.ObjectMapperInstance;
import com.hku.vmlbackend.dto.DecisionTreeDataDTO;
import com.hku.vmlbackend.dto.DecisionTreePredictDTO;
import com.hku.vmlbackend.dto.DecisionTreeTrainDTO;
import com.hku.vmlbackend.dto.LinearRegressionEpochDataDTO;
import com.hku.vmlbackend.service.DecisionTreeService;
import com.hku.vmlbackend.service.FileService;
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

@Service
@Slf4j
public class DecisionTreeServiceImpl implements DecisionTreeService {
    @Value("${python.server.url}")
    private String pythonServerUrl;
    @Autowired
    private FileService fileService;
    @Autowired
    private SocketIOServer socketIOServer;

    private final ObjectMapper objectMapper = ObjectMapperInstance.INSTANCE.getObjectMapper();

    @Override
    public void train(DecisionTreeTrainDTO dto) {
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

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                pythonServerUrl + "/decision-tree/train",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to train model: " + response.getStatusCode());
        }
    }

    @Override
    public void getData(DecisionTreeDataDTO dto) {
        //TODO 将数据传给前端
        log.info("Get data: {}", dto.getData());
    }

    @Override
    public void predict(DecisionTreePredictDTO dto) {
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

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                pythonServerUrl + "/decision-tree/predict",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to predict: " + response.getStatusCode());
        }
    }


}
