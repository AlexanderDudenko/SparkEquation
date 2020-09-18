package com.sparkequation.spring.trial.api.controller.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class SqlErrorsHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handle() {
        Map<String, String> responseBody = Collections.singletonMap("error", "An error has occurred while saving the data to the database");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
