package com.hku.vmlbackend.controller;

import com.hku.vmlbackend.common.result.Result;
import com.hku.vmlbackend.dto.EpochDataDTO;
import com.hku.vmlbackend.dto.LogicRegressionTrainDTO;
import com.hku.vmlbackend.service.LogicRegressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logic-regression")
@CrossOrigin(origins = "*")  // 允许所有来源的跨域请求
public class LogicRegressionController {

    @Autowired
    private LogicRegressionService logicRegressionService;

    @PostMapping("/train")
    public Result train(@RequestBody LogicRegressionTrainDTO dto) {
        logicRegressionService.train(dto);
        return Result.success();
    }

    @PostMapping("/get-epoch-data")
    public Result getEpochData(@RequestBody EpochDataDTO dto) {
        logicRegressionService.getEpochData(dto);
        return Result.success();
    }
}
