package com.oroin.identity_service.features.auth.controller;

import com.oroin.identity_service.features.auth.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
    public Object verifyEmail(@RequestParam("email") String email, HttpServletRequest request) {
        return otpService.generateOtp(email, request.getRemoteAddr());
    }
}
