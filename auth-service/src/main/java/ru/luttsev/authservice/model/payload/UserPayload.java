package ru.luttsev.authservice.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserPayload(
        String id,

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("last_name")
        String lastName,

        @JsonProperty("is_blocked")
        boolean isBlocked
) {
}
