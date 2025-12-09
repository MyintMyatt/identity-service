package com.oroin.identity_service.features.auth.controller;

import com.oroin.identity_service.common.model.response.ApiResponse;
import com.oroin.identity_service.common.utils.ResponseUtils;
import com.oroin.identity_service.features.auth.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "${api.base.path}/{version}/auth", version = "1")
@RequiredArgsConstructor
public class AuthController {

    private final OtpService otpService;

    @GetMapping(value = "hello", version = "1.0")
    public Map<String, String> hello(HttpServletRequest request) {
        System.err.println(request.getRemoteAddr() + " - " + request.getLocalAddr());
        return Map.of("msg", "hello - remote -" + request.getRemoteAddr() + " , local - " + request.getLocalAddr());
    }

    @PostMapping(value = "/verify-email", version = "1.0")
    public ResponseEntity<ApiResponse> verifyEmail(@RequestParam("email") String email, HttpServletRequest request) {
        return ResponseUtils.buildApiResponse(request, otpService.generateOtp(email, request.getRemoteAddr()));
    }

    @PostMapping(value = "/verify-otp", version = "1.0")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String otpCode, HttpServletRequest request){
        return ResponseUtils.buildApiResponse(request, otpService.verifyOtp(email, otpCode, request.getRemoteAddr()));
    }
}
