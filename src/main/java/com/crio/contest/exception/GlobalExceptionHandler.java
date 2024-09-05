package com.crio.contest.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @SuppressWarnings("null")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        String errorMessage = ex.getBindingResult()
                                .getFieldError()
                                .getDefaultMessage(); // Fetching first error message
        
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("message", errorMessage);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        String errorMessage = "Malformed JSON request: " + ex.getLocalizedMessage();

        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("message", errorMessage);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle "User Not Found" errors or any custom 404 errors
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        String errorMessage = ex.getReason() != null ? ex.getReason() : "Resource not found";

        errorResponse.put("status", ex.getStatusCode().value());
        errorResponse.put("message", errorMessage);

        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }
}
