package com.oroin.identity_service.security.oauth2.google.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oroin.identity_service.security.oauth2.google.dto.reqeust.GoogleUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
public class GoogleApiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${google.token-info-url}")
    private String TOKEN_INFO_URL;

    @Value("${google.user-info-url}")
    private String USER_INFO_URL;

    protected Map<String, String> getTokenInfo(String accessToken) throws JsonProcessingException {
        ResponseEntity<Map> response = restTemplate.exchange(
                TOKEN_INFO_URL + accessToken,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Map.class
        );
        if (response.getStatusCode() != HttpStatus.OK)
            throw new IllegalStateException("Invalid Google access token");

        return response.getBody();
    }

    protected GoogleUserInfo getUserProfile(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        ResponseEntity<String> response = restTemplate.exchange(
                USER_INFO_URL,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        if (response.getStatusCode() != HttpStatus.OK)
            throw new IllegalStateException("Failed to fetch Google user profile");

        Map<String, Object> userInfo = objectMapper.readValue(response.getBody(), Map.class);

        return GoogleUserInfo.builder()
                .email((String) userInfo.get("email"))
                .name((String) userInfo.get("name"))
                .givenName((String) userInfo.get("given_name"))
                .familyName((String) userInfo.get("family_name"))
                .picture((String) userInfo.get("picture"))
                .emailVerified((Boolean) userInfo.get("verified_email"))
                .googleId((String) userInfo.get("id"))
                .build();
    }

}
