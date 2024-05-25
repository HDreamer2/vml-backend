package com.hku.vmlbackend.service.impl;

import com.hku.vmlbackend.common.MD5Utils;
import com.hku.vmlbackend.common.result.Result;
import com.hku.vmlbackend.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {
    // 假设使用一个简单的Map来存储MD5和文件路径的映射
    // 实际应用中应使用数据库
    private Map<String, String> fileStorage = new HashMap<>();
    private final static String PATH = "D:\\uploads";
    @Override
    public void uploadCsvFile(MultipartFile file) {
        String path = PATH + File.separator + file.getOriginalFilename();

        try {
            // 将文件保存到指定路径
            File dest = new File(path);
            // 保存文件
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);

            fileStorage.put(MD5Utils.calculateMD5(dest),path);



        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getFileByMD5(String md5) {
        String path = fileStorage.get(md5);
        if (path == null) {
            return null;
        }
        return new File(path);
    }
}
