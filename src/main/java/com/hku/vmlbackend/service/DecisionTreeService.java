package com.hku.vmlbackend.service;

import com.hku.vmlbackend.dto.DecisionTreeDataDTO;
import com.hku.vmlbackend.dto.DecisionTreePredictDTO;
import com.hku.vmlbackend.dto.DecisionTreeTrainDTO;
import com.hku.vmlbackend.entity.DecisionTreeData;

import java.util.List;

public interface DecisionTreeService {
    void train(DecisionTreeTrainDTO dto);

    void getData(DecisionTreeDataDTO dto);

    void predict(DecisionTreePredictDTO dto);

    List<DecisionTreeData> getHttpData(Integer userId, String fileId);
}
