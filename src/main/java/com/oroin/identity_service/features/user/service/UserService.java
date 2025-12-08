package com.oroin.identity_service.features.user.service;

import com.oroin.identity_service.common.model.response.ApiResponse;

import com.oroin.identity_service.features.user.dto.reqesut.GoogleCodeRequest;
import com.oroin.identity_service.features.user.dto.reqesut.UserRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ApiResponse registerUser(UserRequest userRequest);

    ApiResponse exchangeGoogleCodeAndGetToken(GoogleCodeRequest googleCodeRequest);
}
