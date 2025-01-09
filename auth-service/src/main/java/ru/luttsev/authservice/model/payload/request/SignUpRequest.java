package ru.luttsev.authservice.model.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.luttsev.authservice.model.validator.annotation.NotBlankNullable;
import ru.luttsev.authservice.model.validator.annotation.Password;
import ru.luttsev.authservice.model.validator.annotation.SizeNullable;

public record SignUpRequest(
        @JsonProperty("first_name")
        @NotBlank(message = "{first-name.not-blank-and-size}")
        @Size(min = 1, max = 255, message = "{first-name.not-blank-and-size}")
        String firstName,

        @JsonProperty("last_name")
        @NotBlankNullable(message = "{last-name.not-blank-and-size}")
        @SizeNullable(min = 1, max = 255, message = "{last-name.not-blank-and-size}")
        String lastName,

        @Email(regexp = "^\\S+@\\S+\\.\\S+$", message = "{email.regexp}")
        String email,

        @Password
        String password
) {
}
