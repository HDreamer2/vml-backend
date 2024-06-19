package com.hku.vmlbackend.service.impl;

import com.hku.vmlbackend.common.MD5Utils;
import com.hku.vmlbackend.config.MinioConfig;
import com.hku.vmlbackend.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    // 假设使用一个简单的Map来存储MD5和文件路径的映射
    // TODO 使用数据库存储
    private Map<String, String> fileStorage = new HashMap<>();
    private final static String PATH = "D:\\uploads";
    // 从配置文件中读取文件上传路径
    @Value("${file.upload.path}")
    private String uploadPath;


    @Autowired
    private MinioConfig minioConfig;
    @Override
    public void uploadCsvFile(MultipartFile file) {
        String path = PATH + File.separator + file.getOriginalFilename();
//        String path = uploadPath + File.separator + file.getOriginalFilename();
        try {
             //将文件保存到指定路径
            File dest = new File(path);
            // 保存文件
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);

            String MD5 = MD5Utils.calculateMD5(dest);
            fileStorage.put(MD5,path);
            log.info("md5:{}",MD5);
//            String url = minioConfig.putObject(file);
//            log.info("上传成功，url: {}", url);
        } catch (Exception e) {
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
