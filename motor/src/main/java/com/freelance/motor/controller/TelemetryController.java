package com.freelance.motor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelance.motor.dto.TelemetryDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/telemetry")
public class TelemetryController {
    
    @PostMapping
    public ResponseEntity<Void> postMethodName(@Valid @RequestBody TelemetryDTO request) {
        return ResponseEntity.accepted().build();
    }
    
}
