package com.hku.vmlbackend.controller;

import com.hku.vmlbackend.common.result.Result;
import com.hku.vmlbackend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*")  // 允许所有来源的跨域请求
public class FileController {

    @Autowired
    private FileService fileService;


    @PostMapping("/upload-csv")
    public Result uploadCsvFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("Please select a file to upload");
        }
        try {
            fileService.uploadCsvFile(file);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());

        }
        return Result.success();
    }
}
