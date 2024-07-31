package com.hku.vmlbackend.controller;

import com.hku.vmlbackend.common.result.Result;
import com.hku.vmlbackend.dto.DecisionTreeDataDTO;
import com.hku.vmlbackend.dto.DecisionTreePredictDTO;
import com.hku.vmlbackend.dto.DecisionTreeTrainDTO;
import com.hku.vmlbackend.service.DecisionTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/decision-tree")
@CrossOrigin(origins = "*")  // 允许所有来源的跨域请求
public class DecisionTreeController {

    @Autowired
    private DecisionTreeService decisionTreeService;

    @PostMapping("/train")
    public Result train(@RequestBody DecisionTreeTrainDTO dto) {
        decisionTreeService.train(dto);
        return Result.success();
    }

    @PostMapping("/get-data")
    public Result getData(@RequestBody DecisionTreeDataDTO dto) {
        decisionTreeService.getData(dto);
        return Result.success();
    }

    @PostMapping("/predict")
    public Result predict(@RequestBody DecisionTreePredictDTO dto) {
        decisionTreeService.predict(dto);
        return Result.success();
    }
    @GetMapping("/get-http-data")
    public Result getHttpData(@RequestParam Integer userId,@RequestParam String fileId){
        return Result.success(decisionTreeService.getHttpData(userId,fileId));
    }

}
