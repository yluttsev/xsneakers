package ru.luttsev.authservice.model.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.luttsev.authservice.model.validator.annotation.Password;

public record SignInRequest(
        @NotBlank(message = "{email.not-blank}")
        @Size(min = 3, max = 320, message = "{email.size}")
        @Email(message = "{email.regexp}")
        String email,

        @Password
        String password
) {
}
