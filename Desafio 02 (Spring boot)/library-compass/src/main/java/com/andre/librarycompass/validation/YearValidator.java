package com.andre.librarycompass.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class YearValidator implements ConstraintValidator<Year, Integer> {
    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        if(year == null) return true;
        int currentYear = LocalDateTime.now().getYear();
        return year <= currentYear;
    }
}
