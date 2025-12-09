package com.oroin.identity_service.features.auth.service;

import com.oroin.identity_service.common.model.response.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public interface OtpService {
    ApiResponse generateOtp(String email, String ipAddr);

    ApiResponse verifyOtp(String email, String otp, String remoteAddr);
}
