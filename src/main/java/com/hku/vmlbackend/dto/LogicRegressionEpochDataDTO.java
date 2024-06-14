package com.hku.vmlbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class LogicRegressionEpochDataDTO {
    private Integer epoch;
    private List<List<Double>> weights;
    private List<Double> bias;
    private Double loss;
    private List<Double> featureWeights;
}
