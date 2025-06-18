package org.example.dataprocessorservice.controller;

import org.example.dataprocessorservice.exception.BaseAppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseAppException.class)
    public ResponseEntity<Map<String, Object>> handleBaseAppException(BaseAppException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("code", ex.getCode());
        errorBody.put("message", ex.getMessage());
        errorBody.put("status", ex.getStatusCode().toString());
        errorBody.put("timestamp", ZonedDateTime.now());

        return ResponseEntity
                .status(ex.getStatusCode().value())
                .body(errorBody);
    }
}
