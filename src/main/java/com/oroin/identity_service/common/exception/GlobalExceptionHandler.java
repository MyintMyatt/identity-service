package com.oroin.identity_service.common.exception;

import com.oroin.identity_service.common.model.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse handleGlobalException(Exception e) {
        return ApiResponse.builder()
                .code(500)
                .success(0)
                .message(e.getMessage())
                .data(null)
                .build();
    }
}
