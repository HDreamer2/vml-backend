package com.hku.vmlbackend.dto;

import lombok.Data;

@Data
public class LogicRegressionTrainDTO {
    private String[] features;
    private String label;
    private Integer epoch;
    private String md5;
}
