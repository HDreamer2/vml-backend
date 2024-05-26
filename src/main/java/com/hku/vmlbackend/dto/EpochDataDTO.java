package com.hku.vmlbackend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EpochDataDTO implements Serializable {
    private Integer epoch;
    private List<List<Double>> weights;
    private List<Double> bias;
    private Double loss;
}
