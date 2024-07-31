package com.hku.vmlbackend.service;

import com.hku.vmlbackend.dto.LogisticRegressionEpochDataDTO;
import com.hku.vmlbackend.dto.LogisticRegressionTrainDTO;
import com.hku.vmlbackend.entity.LinearRegressionData;
import com.hku.vmlbackend.entity.LogisticRegressionData;

import java.util.List;

public interface LogisticRegressionService {
    void train(LogisticRegressionTrainDTO dto);
    void getEpochData(LogisticRegressionEpochDataDTO dto);
    List<LogisticRegressionData> getHttpData(Integer userId, String fileId);
}
