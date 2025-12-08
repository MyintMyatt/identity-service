package com.oroin.identity_service.security.oauth2.google.service;

import org.springframework.stereotype.Service;

@Service
public interface ExchangeGoogleCodeService {
    String exchangeGoogleCodeForToken(final String code);
}
