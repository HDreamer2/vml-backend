package com.hku.vmlbackend.dto;

import lombok.Data;

@Data
public class DecisionTreeTrainDTO {
    private String[] features;
    private String label;
    private String fileId;
    private Integer  userId ;
}
