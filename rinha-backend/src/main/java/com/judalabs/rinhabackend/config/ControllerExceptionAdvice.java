package com.judalabs.rinhabackend.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.judalabs.rinhabackend.exception.NotFoundException;
import com.judalabs.rinhabackend.exception.UnprocessableEntityException;

@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler({DataIntegrityViolationException.class, UnprocessableEntityException.class})
    public ResponseEntity<String> handleUnprocessableEntityException(Exception e) {
        return ResponseEntity.unprocessableEntity().body("unprocessable entity");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleBadRequest(Exception exception) {
        return ResponseEntity.badRequest().body("bad request");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(Exception exception) {
        return ResponseEntity.notFound().build();
    }

}
