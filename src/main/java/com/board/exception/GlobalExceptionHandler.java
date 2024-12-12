package com.board.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.board.common.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String message = error.getDefaultMessage();
            errorMessage.append(fieldName).append(": ").append(message).append("; ");
        });

        return ResponseEntity.badRequest().body(
            new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage.toString(),
                "VALIDATION_ERROR",
                request.getDescription(false),
                LocalDateTime.now()
            )
        );
    }
    
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        		new ErrorResponse(
        				HttpStatus.NOT_FOUND.value(),
        				ex.getMessage(),
        				HttpStatus.NOT_FOUND.name(),
        				request.getDescription(false),
        				LocalDateTime.now()
        		)
		);
    }
    
    @ExceptionHandler(UnauthorizeException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizeException(UnauthorizeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
        		new ErrorResponse(
        				HttpStatus.FORBIDDEN.value(),
        				ex.getMessage(),
        				HttpStatus.FORBIDDEN.name(),
        				request.getDescription(false),
        				LocalDateTime.now()
        		)
		);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(RuntimeException ex, WebRequest request) {
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
    			new ErrorResponse(
        				HttpStatus.INTERNAL_SERVER_ERROR.value(),
        				ex.getMessage(),
        				HttpStatus.INTERNAL_SERVER_ERROR.name(),
        				request.getDescription(false),
        				LocalDateTime.now()
        		)
		);
    }
}
