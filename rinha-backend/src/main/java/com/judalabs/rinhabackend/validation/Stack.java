package com.judalabs.rinhabackend.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = StackValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Stack {

    String message() default "stack incorreta";

    Class<?> [] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
