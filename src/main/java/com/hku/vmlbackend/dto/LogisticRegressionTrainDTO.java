package com.hku.vmlbackend.dto;

import lombok.Data;

@Data
public class LogisticRegressionTrainDTO {
    private String[] features;
    private String label;
    private Integer epoch;
    private String fileId;
}
