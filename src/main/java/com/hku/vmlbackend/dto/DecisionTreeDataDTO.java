package com.hku.vmlbackend.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.io.Serializable;

@Data
public class DecisionTreeDataDTO implements Serializable {
    JsonNode data;
}
