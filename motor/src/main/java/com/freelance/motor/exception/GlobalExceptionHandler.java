package com.freelance.motor.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException exc){
        List<String> errors = exc.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(FieldError::getDefaultMessage)
        .collect(Collectors.toList());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            LocalDateTime.now(ZoneId.of("America/Sao_Paulo")), 
            HttpStatus.BAD_REQUEST.value(), 
            "Validation error", 
            errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            LocalDateTime.now(ZoneId.of("America/Sao_Paulo")), 
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            "Internal server error", 
            List.of(ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
