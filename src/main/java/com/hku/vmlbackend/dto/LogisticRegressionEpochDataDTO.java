package com.hku.vmlbackend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LogisticRegressionEpochDataDTO implements Serializable {
    private String fileId;
    private Integer  userId ;
    private Integer epoch;
    private List<List<Double>> weights;
    private List<Double> bias;
    private Double loss;
    private List<List<Double>> featureWeights;

}
