package com.board.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Map<String, String>> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            String errorCode = "VALIDATION_ERROR";

            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("errorCode", errorCode);
            errorDetails.put("errorMessage", errorMessage);

            errors.put(fieldName, errorDetails);
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
