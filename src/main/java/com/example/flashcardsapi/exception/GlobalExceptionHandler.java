package com.example.flashcardsapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FlashcardsApiException> constraintViolationException(ConstraintViolationException exception){
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        StringBuilder errors = new StringBuilder();

        constraintViolations.forEach(violation ->
            errors.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("\\n ")
        );

        FlashcardsApiException exceptionToReturn = new FlashcardsApiException(HttpStatus.BAD_REQUEST, errors.toString());

        return new ResponseEntity<>(exceptionToReturn, new HttpHeaders(), exceptionToReturn.getStatus());

    }
}
