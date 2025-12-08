package com.oroin.identity_service.common.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ApiResponse {
    private int success;
    private int code;
    private String message;
    private Object data;
    private Map<String, Object> metadata;
}
