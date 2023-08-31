package com.judalabs.rinhabackend.validation;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StackValidator implements ConstraintValidator<Stack, List<String>> {

    @Override
    public boolean isValid(List<String> stack, ConstraintValidatorContext context) {
        if(stack == null) return true;

        for(String item : stack) {
            if("1".equals(item) || item.length() > 32) return false;
        }
        return true;

    }
}
