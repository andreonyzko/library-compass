package com.andre.librarycompass.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = YearValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Year{
    String message() default "Ano de publicação não pode ser no futuro";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
