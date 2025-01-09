package ru.luttsev.authservice.model.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.luttsev.authservice.model.validator.SizeNullableValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SizeNullableValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SizeNullable {
    String message() default "{}";

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
