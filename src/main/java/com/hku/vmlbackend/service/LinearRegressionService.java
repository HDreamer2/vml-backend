package com.hku.vmlbackend.service;

import com.hku.vmlbackend.dto.LinearRegressionEpochDataDTO;
import com.hku.vmlbackend.dto.LinearRegressionTrainDTO;

public interface LinearRegressionService {
    void train(LinearRegressionTrainDTO dto);
    void getEpochData(LinearRegressionEpochDataDTO dto);


}
