package com.hku.vmlbackend.service;

import com.hku.vmlbackend.dto.LinearRegressionEpochDataDTO;
import com.hku.vmlbackend.dto.LinearRegressionTrainDTO;
import com.hku.vmlbackend.entity.LinearRegressionData;
import com.hku.vmlbackend.entity.RandomForestData;

import java.util.List;

public interface LinearRegressionService {
    void train(LinearRegressionTrainDTO dto);
    void getEpochData(LinearRegressionEpochDataDTO dto);


    List<LinearRegressionData> getHttpData(Integer userId, String fileId);
}
