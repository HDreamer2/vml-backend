package com.hku.vmlbackend.service;

import com.hku.vmlbackend.dto.LogisticRegressionEpochDataDTO;
import com.hku.vmlbackend.dto.LogisticRegressionTrainDTO;

public interface LogisticRegressionService {
    void train(LogisticRegressionTrainDTO dto);
    void getEpochData(LogisticRegressionEpochDataDTO dto);
}
