package com.oroin.identity_service.features.user.service;

import com.oroin.identity_service.security.oauth2.google.dto.reqeust.GoogleUserInfo;
import com.oroin.identity_service.security.oauth2.google.dto.response.GoogleOAuthResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface AuthService {

    GoogleOAuthResponse processGoogleOAuthAndGenerateJWT(GoogleUserInfo googleUserInfo) throws IOException;
//    GithubOAuthResponse processGithubOAuth(GithubUserInfo githubUserInfo) throws MessagingException, IOException;
}
