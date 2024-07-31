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
public class RandomForestData {

    private int id;

    private int treeIndex;

    private String features; // 假设features是一个整数列表

    private String tree;

    private Integer  userId ;

    private String fileId;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
