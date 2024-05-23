package com.hku.vmlbackend.controller;

import com.hku.vmlbackend.common.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@RestController
@RequestMapping("/api")
public class FileController {

    @Value("${python.server.url}")
    private String pythonServerUrl;

    private final static String PATH = "D:\\uploads";

    @PostMapping("/upload-csv")
    public Result uploadCsvFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("Please select a file to upload");
        }

        String path = PATH + File.separator + file.getOriginalFilename();

        try {
            // 将文件保存到指定路径
            File dest = new File(path);
            // 保存文件
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            System.out.println("File saved to: " + dest.getAbsolutePath());
            file.transferTo(dest);

            // 将文件传递给Python后端
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // 创建MultiValueMap用于传递文件
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(dest));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    pythonServerUrl + "/upload-csv",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            return Result.success(response.getBody());
        } catch (IOException e) {
            return Result.error(e.getMessage());
        }
    }
}