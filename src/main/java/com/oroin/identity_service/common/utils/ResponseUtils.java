package com.oroin.identity_service.common.utils;

import com.oroin.identity_service.common.model.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ResponseUtils {

    public static ResponseEntity<ApiResponse> buildApiResponse(final HttpServletRequest request, final ApiResponse apiResponse) {
        final HttpStatus status = HttpStatus.valueOf(apiResponse.getCode());
        if (apiResponse.getMetadata() == null) {
            final String method = request.getMethod();
            final String endpoint = request.getRequestURI();
            apiResponse.setMetadata(new HashMap<>());
            apiResponse.getMetadata().put("method", method);
            apiResponse.getMetadata().put("endpoint", endpoint);
        }
        return new ResponseEntity<ApiResponse>(apiResponse, status);
    }
}
