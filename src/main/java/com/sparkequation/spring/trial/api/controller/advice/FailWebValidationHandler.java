package com.sparkequation.spring.trial.api.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class FailWebValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handle(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<String> errors = new ArrayList<>(fieldErrors.size());
        fieldErrors.forEach(error -> errors.add(error.getDefaultMessage()));

        Map<String, List<String>> responseBody = Collections.singletonMap("validationErrors", errors);
        return ResponseEntity.badRequest().body(responseBody);
    }
}
