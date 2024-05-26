package com.hku.vmlbackend.service;

import com.hku.vmlbackend.dto.EpochDataDTO;
import com.hku.vmlbackend.dto.LinearRegressionTrainDTO;

public interface LinearRegressionService {
    void train(LinearRegressionTrainDTO dto);
    void getEpochData(EpochDataDTO dto);


}
