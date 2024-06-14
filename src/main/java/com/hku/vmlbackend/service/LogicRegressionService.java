package com.hku.vmlbackend.service;

import com.hku.vmlbackend.dto.EpochDataDTO;
import com.hku.vmlbackend.dto.LogicRegressionTrainDTO;

public interface LogicRegressionService {
    void train(LogicRegressionTrainDTO dto);
    void getEpochData(EpochDataDTO dto);
}
