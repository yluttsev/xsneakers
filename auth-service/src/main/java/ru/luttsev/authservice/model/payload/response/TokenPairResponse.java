package ru.luttsev.authservice.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenPairResponse(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("access_expiration")
        long accessExpiration
) {
}
