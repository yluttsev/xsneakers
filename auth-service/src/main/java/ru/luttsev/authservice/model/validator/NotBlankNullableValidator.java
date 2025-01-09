package ru.luttsev.authservice.model.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.luttsev.authservice.model.validator.annotation.NotBlankNullable;

import java.util.Objects;

public class NotBlankNullableValidator implements ConstraintValidator<NotBlankNullable, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            return !value.trim().isEmpty();
        }
        return true;
    }
}
