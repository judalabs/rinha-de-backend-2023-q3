package com.judalabs.rinhabackend.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.judalabs.rinhabackend.exception.UnprocessableEntityException;

@ControllerAdvice
public class ControllerExceptionAdvice {


    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<Void> handleUnprocessableEntityException(Exception e) {
        return ResponseEntity.unprocessableEntity().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public String handleConstraintViolation(Exception exception) {
        return "Request Inválida";
    }

}
