package ru.luttsev.authservice.model.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.luttsev.authservice.model.validator.annotation.SizeNullable;

import java.util.Objects;

public class SizeNullableValidator implements ConstraintValidator<SizeNullable, String> {

    private int min;
    private int max;

    @Override
    public void initialize(SizeNullable constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            return value.length() >= min && value.length() <= max;
        }
        return true;
    }
}
