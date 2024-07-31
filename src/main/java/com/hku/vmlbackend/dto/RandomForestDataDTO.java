package com.hku.vmlbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RandomForestDataDTO implements Serializable {
    @JsonProperty("tree_idx")
    private int treeIndex;

    @JsonProperty("features")
    private List<Integer> features; // 假设features是一个整数列表

    @JsonProperty("tree")
    private List<Object> tree;
    private Integer  userId ;
    private String fileId;

}
