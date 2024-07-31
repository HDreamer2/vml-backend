package com.hku.vmlbackend.controller;

import com.hku.vmlbackend.common.result.Result;
import com.hku.vmlbackend.dto.RandomForestDataDTO;
import com.hku.vmlbackend.dto.RandomForestPredictDTO;
import com.hku.vmlbackend.dto.RandomForestTrainDTO;
import com.hku.vmlbackend.service.RandomForestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/random-forest")
@CrossOrigin(origins = "*")  // 允许所有来源的跨域请求
public class RandomForestController {

    @Autowired
    private RandomForestService randomForestService;

    @PostMapping("/train")
    public Result train(@RequestBody RandomForestTrainDTO dto) {
        randomForestService.train(dto);
        return Result.success();
    }

    @PostMapping("/get-data")
    public Result getData(@RequestBody List<RandomForestDataDTO> dtos) {
        dtos.forEach(randomForestService::getData);
        return Result.success();
    }

    @PostMapping("/predict")
    public Result predict(@RequestBody RandomForestPredictDTO dto) {
        randomForestService.predict(dto);
        return Result.success();
    }

    @GetMapping("/get-http-data")
    public Result getHttpData(@RequestParam Integer userId,@RequestParam String fileId){
        return Result.success(randomForestService.getHttpData(userId,fileId));
    }

}
