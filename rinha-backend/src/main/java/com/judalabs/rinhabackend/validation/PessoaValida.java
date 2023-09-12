package com.judalabs.rinhabackend.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = PessoaValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PessoaValida {

    String message() default "stack incorreta";

    Class<?> [] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
