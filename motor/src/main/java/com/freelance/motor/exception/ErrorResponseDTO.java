package com.freelance.motor.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
    LocalDateTime timestamp,
    int status, 
    String error,
    List<String> messages
){}
