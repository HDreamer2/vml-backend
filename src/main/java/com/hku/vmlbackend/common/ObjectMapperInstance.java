package com.hku.vmlbackend.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

@Getter
public enum ObjectMapperInstance {

    INSTANCE;

    private final ObjectMapper objectMapper = new ObjectMapper();

    ObjectMapperInstance() {

    }
}
