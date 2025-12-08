package com.oroin.identity_service.features.user.controller;

import com.oroin.identity_service.common.model.request_dto_gp.Create;
import com.oroin.identity_service.common.model.response.ApiResponse;
import com.oroin.identity_service.common.utils.ResponseUtils;

import com.oroin.identity_service.features.user.dto.reqesut.GoogleCodeRequest;
import com.oroin.identity_service.features.user.dto.reqesut.UserRequest;
import com.oroin.identity_service.features.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth-service/api/v1/auth/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse registerUser(@Validated(Create.class) UserRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/exchangeGoogle")
    public ResponseEntity<ApiResponse> exchangeGoogleCode(@RequestBody GoogleCodeRequest codeRequest, HttpServletRequest request) {
        System.err.println("call controller");
        return ResponseUtils.buildApiResponse(request, userService.exchangeGoogleCodeAndGetToken(codeRequest));
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return ResponseEntity.of(Optional.of(Map.of("msg", "helo"))
        );
    }


}
