package com.doroz.article_service.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse response = buildResponse(
                HttpStatus.BAD_REQUEST,
                ErrorMessages.VALIDATION_FAILED,
                request.getRequestURI(),
                errors
        );

        log.warn("Validation error occurred: {}", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        ErrorResponse response = buildResponse(
                HttpStatus.NOT_FOUND,
                ErrorMessages.RESOURCE_NOT_FOUND,
                request.getRequestURI(),
                null
        );

        log.warn("Resource not found: {}", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        ErrorResponse response = buildResponse(
                HttpStatus.CONFLICT,
                ErrorMessages.DATA_INTEGRITY_VIOLATION,
                request.getRequestURI(),
                null
        );

        log.warn("Data integrity violation: {}", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, HttpServletRequest request) {
        ErrorResponse response = buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorMessages.INTERNAL_SERVER_ERROR,
                request.getRequestURI(),
                null
        );

        log.error("Unhandled exception", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse buildResponse(HttpStatus status, String message, String path, Map<String, String> details) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                message,
                path,
                details
        );
    }
}
