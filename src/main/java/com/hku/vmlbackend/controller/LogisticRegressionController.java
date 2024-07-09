package com.hku.vmlbackend.controller;

import com.hku.vmlbackend.common.result.Result;
import com.hku.vmlbackend.dto.LogisticRegressionEpochDataDTO;
import com.hku.vmlbackend.dto.LogisticRegressionTrainDTO;

import com.hku.vmlbackend.service.LogisticRegressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logistic-regression")
@CrossOrigin(origins = "*")  // 允许所有来源的跨域请求
public class LogisticRegressionController {

    @Autowired
    private LogisticRegressionService logisticRegressionService;

    @PostMapping("/train")
    public Result train(@RequestBody LogisticRegressionTrainDTO dto) {
        logisticRegressionService.train(dto);
        return Result.success();
    }

    @PostMapping("/get-epoch-data")
    public Result getEpochData(@RequestBody LogisticRegressionEpochDataDTO dto) {
        logisticRegressionService.getEpochData(dto);
        return Result.success();
    }
}
