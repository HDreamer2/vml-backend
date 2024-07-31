package com.hku.vmlbackend.service;

import com.hku.vmlbackend.common.result.Result;
import com.hku.vmlbackend.dto.*;
import com.hku.vmlbackend.entity.RandomForestData;

import java.util.List;

public interface RandomForestService {
    void train(RandomForestTrainDTO dto);


    void getData(RandomForestDataDTO dto);

    void predict(RandomForestPredictDTO dto);

    List<RandomForestData> getHttpData(Integer userId, String fileId);


}
