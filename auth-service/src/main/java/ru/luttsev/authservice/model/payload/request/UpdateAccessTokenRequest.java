package ru.luttsev.authservice.model.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UpdateAccessTokenRequest(
        @JsonProperty("refresh_token")
        @NotBlank(message = "{refresh-token.not-blank}")
        String refreshToken
) {
}
