package com.hku.vmlbackend.controller;

import com.hku.vmlbackend.common.result.Result;
import com.hku.vmlbackend.service.LinearRegressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/linear-regression")
public class LinearRegressionController {

    @Autowired
    private LinearRegressionService linearRegressionService;

    @PostMapping("/train")
    public Result train(String[] features, String label, int epoch, String MD5) {
        linearRegressionService.train(features, label, epoch, MD5);
        return Result.success();
    }

    @PostMapping("/getEpochData")
    //TODO 从Python后端获取训练过程中的数据
    public Result getEpochData() {

        return Result.success();
    }
}
