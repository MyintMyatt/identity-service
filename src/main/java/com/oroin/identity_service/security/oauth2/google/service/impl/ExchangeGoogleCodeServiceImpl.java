package com.oroin.identity_service.security.oauth2.google.service.impl;

import com.oroin.identity_service.security.oauth2.google.service.ExchangeGoogleCodeService;
import org.antlr.v4.runtime.misc.MultiMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class ExchangeGoogleCodeServiceImpl implements ExchangeGoogleCodeService {

    @Value("${google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${google.client-secret}")
    private String GOOGLE_SECRET_KEY;

    @Value("${google.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${google.exchange-code-token-url}")
    private String EXCHANGE_CODE_TOKEN_URL;


    @Override
    public String exchangeGoogleCodeForToken(String code) {

        RestTemplate restTemplate = new RestTemplate();


        String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", decodedCode);
        params.add("client_id", GOOGLE_CLIENT_ID);
        params.add("client_secret", GOOGLE_SECRET_KEY);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("grant_type", "authorization_code");

        // Set headers to x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // Call google token endpoint
        ResponseEntity<Map> response = restTemplate.exchange(
                EXCHANGE_CODE_TOKEN_URL,
                HttpMethod.POST,
                request,
                Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        } else {
            throw new RuntimeException("Failed to exchange code for access token: " + response.getStatusCode());
        }
    }
}

//47,50