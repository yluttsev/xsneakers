package ru.luttsev.authservice.model.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.luttsev.authservice.model.validator.NotBlankNullableValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotBlankNullableValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankNullable {
    String message() default "{}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
