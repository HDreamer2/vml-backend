package com.hku.vmlbackend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {
    void uploadCsvFile(MultipartFile file);

    File getFileByMD5(String MD5);
}
