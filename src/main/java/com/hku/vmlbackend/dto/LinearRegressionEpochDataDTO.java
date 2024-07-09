package com.hku.vmlbackend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LinearRegressionEpochDataDTO implements Serializable {
    //TODO 修改数据类型以匹配python端
    private Integer epoch;
    private List<List<Double>> weights;
    private List<Double> bias;
    private Double loss;
}
