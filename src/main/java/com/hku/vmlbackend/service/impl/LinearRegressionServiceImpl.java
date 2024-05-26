package com.hku.vmlbackend.service.impl;

import com.hku.vmlbackend.service.FileService;
import com.hku.vmlbackend.service.LinearRegressionService;
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
public class LinearRegressionServiceImpl implements LinearRegressionService {
    @Value("${python.server.url}")
    private String pythonServerUrl;
    @Autowired
    private FileService fileService;

    @Override
    public void train(String[] features, String label, int epoch, String MD5) {
        File file = fileService.getFileByMD5(MD5);
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
        //TODO 添加features, label, epoch参数
        body.add("features", features);
        body.add("label", label);
        body.add("epoch", epoch);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                pythonServerUrl + "/linear-regression",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to train model: " + response.getStatusCode());
        }
        @Override
        public String getEpochData() {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(pythonServerUrl + "/linear-regression/get-epoch-data", String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to get epoch data: " + response.getStatusCode());
            }

            return response.getBody();
        }
    }
}
