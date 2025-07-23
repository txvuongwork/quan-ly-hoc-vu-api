package com.backend.quan_ly_hoc_vu_api.config.jwt;

import java.time.Instant;

public record GenerateJwtResult(
        String tokenId,
        String accessToken,
        String refreshToken,
        Instant expiredDate
) {
}
