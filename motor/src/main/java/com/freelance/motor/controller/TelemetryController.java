package com.freelance.motor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelance.motor.dto.TelemetryDTO;
import com.freelance.motor.entity.Telemetry;
import com.freelance.motor.service.TelemetryService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/telemetry")
public class TelemetryController {
    
    private final TelemetryService telemetryService;

    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @PostMapping
    public ResponseEntity<Telemetry> receiveTelemetry(@Valid @RequestBody TelemetryDTO request) {
        Telemetry telemetry = telemetryService.saveTelemetry(request);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(telemetry);
    }
    
}
