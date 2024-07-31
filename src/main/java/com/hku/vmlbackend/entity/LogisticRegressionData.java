package com.hku.vmlbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AUTHOR:XYS
 * DESCRIPCTION:NO BUG
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticRegressionData {
    private int id;
    private String fileId;
    private Integer  userId ;
    private Integer epoch;
    private String weights;
    private String bias;
    private Double loss;
    private String featureWeights;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
