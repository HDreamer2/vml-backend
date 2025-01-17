package com.hku.vmlbackend.controller;

import com.hku.vmlbackend.common.result.Result;
import com.hku.vmlbackend.dto.LinearRegressionEpochDataDTO;
import com.hku.vmlbackend.dto.LinearRegressionTrainDTO;
import com.hku.vmlbackend.service.LinearRegressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/linear-regression")
@CrossOrigin(origins = "*")  // 允许所有来源的跨域请求
public class LinearRegressionController {

    @Autowired
    private LinearRegressionService linearRegressionService;

    @PostMapping("/train")
    public Result train(@RequestBody LinearRegressionTrainDTO dto) {
        linearRegressionService.train(dto);
        return Result.success();
    }

    @PostMapping("/get-epoch-data")
    public Result getEpochData(@RequestBody LinearRegressionEpochDataDTO dto) {
        linearRegressionService.getEpochData(dto);
        return Result.success();
    }
    @GetMapping("/get-http-data")
    public Result getHttpData(@RequestParam Integer userId,@RequestParam String fileId){
        return Result.success(linearRegressionService.getHttpData(userId,fileId));
    }
}
