package com.hku.vmlbackend.service;

import com.hku.vmlbackend.dto.DecisionTreeDataDTO;
import com.hku.vmlbackend.dto.DecisionTreePredictDTO;
import com.hku.vmlbackend.dto.DecisionTreeTrainDTO;

public interface DecisionTreeService {
    void train(DecisionTreeTrainDTO dto);

    void getData(DecisionTreeDataDTO dto);

    void predict(DecisionTreePredictDTO dto);
}
