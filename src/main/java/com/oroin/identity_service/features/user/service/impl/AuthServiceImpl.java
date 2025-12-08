package com.oroin.identity_service.features.user.service.impl;

import com.oroin.identity_service.common.constant.Provider;
import com.oroin.identity_service.features.user.entity.OAuthUser;
import com.oroin.identity_service.features.user.entity.User;
import com.oroin.identity_service.features.user.mapper.UserMapper;
import com.oroin.identity_service.features.user.repository.OAuthUserRepository;
import com.oroin.identity_service.features.user.repository.UserRepository;
import com.oroin.identity_service.features.user.service.AuthService;
import com.oroin.identity_service.security.oauth2.google.dto.reqeust.GoogleUserInfo;
import com.oroin.identity_service.security.oauth2.google.dto.response.GoogleOAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final OAuthUserRepository oAuthUserRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public GoogleOAuthResponse processGoogleOAuthAndGenerateJWT(GoogleUserInfo googleUserInfo) throws IOException {

        Optional<User> existingUser = userRepository.findUserByEmailAddress(googleUserInfo.getEmail());
        if (existingUser.isPresent()) {
            User user = existingUser.get();

            Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByUserAndProvider(user, Provider.GOOGLE.name());
            if (oAuthUser.isPresent()) {

                /*
                * @generate own jwt token
                * */
                String jwtToken = "clear";

                GoogleOAuthResponse googleOAuthResponse= new GoogleOAuthResponse();
                googleOAuthResponse.setUser(userMapper.toFullUserResponse(user));
                googleOAuthResponse.setProfile_picture(googleUserInfo.getPicture());
                googleOAuthResponse.setToken(jwtToken);
                googleOAuthResponse.setNewUser(false);
                return googleOAuthResponse;
            }else{
                throw new IllegalArgumentException("Already registered with different provider");
            }
        }else {
            /*
            * @ create new user
            * */
            // create user from google
            User user = createUserFromGoogle(googleUserInfo);

            // create oauth user
            createOAuthUser(user, googleUserInfo);

            String jwtToken = "clear";

            // Building Response
            GoogleOAuthResponse response= new GoogleOAuthResponse();
            response.setUser(userMapper.toFullUserResponse(user));
            response.setToken(jwtToken);
            response.setProfile_picture(googleUserInfo.getPicture());
            response.setNewUser(true);
            return response;
        }
    }

    private User createUserFromGoogle(GoogleUserInfo googleUserInfo) {
        User user = new User();
        user.setEmailAddress(googleUserInfo.getEmail());
        user.setUsername(googleUserInfo.getName());
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.setPosition("vendor");
        return userRepository.save(user);
    }

    private void createOAuthUser(User user, GoogleUserInfo googleUserInfo) {
        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setUser(user);
        oAuthUser.setProviderUserId(googleUserInfo.getGoogleId());
        oAuthUser.setProfilePicture(googleUserInfo.getPicture());
        oAuthUser.setEmailVerified(googleUserInfo.getEmailVerified());
        oAuthUser.setProvider(Provider.GOOGLE.name());
        oAuthUserRepository.save(oAuthUser);
    }

}
