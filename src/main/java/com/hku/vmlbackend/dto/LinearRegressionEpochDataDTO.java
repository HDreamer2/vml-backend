package com.hku.vmlbackend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LinearRegressionEpochDataDTO implements Serializable {

    private Integer  userId ;
    private String fileId;
    private Integer epoch;
    private List<List<Double>> weights;
    private List<Double> bias;
    private Double loss;

}
