package com.oroin.identity_service.security.oauth2.google.dto.response;

import com.oroin.identity_service.features.user.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleOAuthResponse{
    private UserResponse user;
    private String token;
    private String profile_picture;
    private boolean isNewUser;
}
