package com.oroin.identity_service.security.oauth2.google.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oroin.identity_service.security.oauth2.google.dto.reqeust.GoogleUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

    private final GoogleApiClient googleApiClient;
    private final GoogleIdTokenVerifierService idTokenVerifierService;

    public GoogleUserInfo verifyIdToken(String idToken) throws GeneralSecurityException, IOException {
        return idTokenVerifierService.verifyIdToken(idToken);
    }

    public GoogleUserInfo verifyAccessToken(String accessToken) throws JsonProcessingException {
        Map<String, String> tokenInfo = googleApiClient.getTokenInfo(accessToken);
        String scope = (String) tokenInfo.get("scope");
        if(scope == null || (!scope.contains("email") && !scope.contains("profile")))
            throw new IllegalArgumentException("Token doesn't have required scopes");

        return googleApiClient.getUserProfile(accessToken);
    }


}
