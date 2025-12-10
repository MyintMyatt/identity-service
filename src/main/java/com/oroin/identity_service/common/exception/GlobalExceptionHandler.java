package com.oroin.identity_service.common.exception;

import com.oroin.identity_service.common.model.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ApiResponse handleGlobalException(Exception e) {
//        return ApiResponse.builder()
//                .code(500)
//                .success(0)
//                .message(e.getMessage())
//                .data(null)
//                .metadata(Map.of("timestamp", LocalDateTime.now()))
//                .build();
//    }
}
