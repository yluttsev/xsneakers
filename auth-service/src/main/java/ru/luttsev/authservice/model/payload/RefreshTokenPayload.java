package ru.luttsev.authservice.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record RefreshTokenPayload(
        UUID id,

        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("issued_at")
        LocalDateTime issuedAt,

        @JsonProperty("expires_at")
        LocalDateTime expiresAt,

        @JsonProperty("user_id")
        UUID userId
) {
}
