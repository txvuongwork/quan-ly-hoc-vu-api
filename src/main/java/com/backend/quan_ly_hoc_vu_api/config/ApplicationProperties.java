package com.backend.quan_ly_hoc_vu_api.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ApplicationProperties {

    CorsConfiguration cors;
    SecurityProperties security;

    public record SecurityProperties(
            JwtProperties jwt
    ) {}

    public record JwtProperties(
            String secret,
            long accessTokenInMinutes,
            long refreshTokenInHours
    ) {}

}

