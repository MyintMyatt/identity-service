package com.oroin.identity_service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "otp")
@Data
public class OtpConfig {
    private int digit;
    private Duration ttl;
    private Duration rateLimitTtl;
    private Duration blockTtl;
    private Duration ipRateLimitTtl;
}
