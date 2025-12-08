package com.oroin.identity_service.features.user.service.impl;

import com.oroin.identity_service.common.model.response.ApiResponse;
import com.oroin.identity_service.security.oauth2.google.dto.reqeust.GoogleUserInfo;
import com.oroin.identity_service.security.oauth2.google.dto.response.GoogleOAuthResponse;
import com.oroin.identity_service.security.oauth2.google.service.ExchangeGoogleCodeService;
import com.oroin.identity_service.security.oauth2.google.service.GoogleOAuthService;
import com.oroin.identity_service.features.user.dto.reqesut.GoogleCodeRequest;
import com.oroin.identity_service.features.user.dto.reqesut.UserRequest;
import com.oroin.identity_service.features.user.service.AuthService;
import com.oroin.identity_service.features.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final ExchangeGoogleCodeService exchangeGoogleCodeService;
    private final GoogleOAuthService googleOAuthService;
    private final AuthService authService;

    @Override
    public ApiResponse registerUser(UserRequest userRequest) {
        return null;
    }

    @Override
    public ApiResponse exchangeGoogleCodeAndGetToken(GoogleCodeRequest googleCodeRequest) {
        /*
        * @ get access token
        * */
        String accessToken = exchangeGoogleCodeService.exchangeGoogleCodeForToken(googleCodeRequest.getCode());
        System.err.println("access token :" + accessToken);
        try {
            GoogleUserInfo googleUserInfo;
            try {
                System.err.println("in try");
                googleUserInfo = googleOAuthService.verifyIdToken(accessToken);
            } catch (Exception e) {
                System.err.println("in cache");
                googleUserInfo = googleOAuthService.verifyAccessToken(accessToken);
            }

            System.err.println("GUser => " + googleUserInfo);

            /*
            * @ Save user, and generate own jwt token
            * */
            GoogleOAuthResponse googleOAuthResponse = authService.processGoogleOAuthAndGenerateJWT(googleUserInfo);

            return ApiResponse.builder()
                    .success(1)
                    .code(HttpStatus.OK.value())
                    .message("Google OAuth Successfully!")
                    .data(googleOAuthResponse)
                    .metadata(Map.of("timestamp", System.currentTimeMillis()))
                    .build();


        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(0)
                    .code(500)
                    .message("Internal server error: " + e.getMessage())
                    .data(null)
                    .metadata(Map.of("timestamp", System.currentTimeMillis()))
                    .build();
        }
    }
}
