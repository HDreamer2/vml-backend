package com.hku.vmlbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * AUTHOR:XYS
 * DESCRIPCTION:NO BUG
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecisionTreeData {

    private int id;

    private Integer  userId ;

    private String fileId;
    private String data;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
