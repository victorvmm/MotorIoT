package com.freelance.motor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelance.motor.dto.TelemetryDTO;
import com.freelance.motor.service.TelemetryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/telemetry")
@Tag(name = "IoT Telemetry", description = "Endpoints for ingesting data from truck sensors")   
public class TelemetryController {
    
    private final TelemetryService telemetryService;

    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @PostMapping
    @Operation(summary = "Receives sensor temperature reading", description = "Evaluates the reading and triggers an asynchronous alert if temperature exceeds the threshold.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "Telemetry reading accepted and scheduled for processing"),
        @ApiResponse(responseCode = "400", description = "Invalid request payload (e.g., missing truck ID or temperature)")
    })
    public ResponseEntity<Void> receiveTelemetry(@Valid @RequestBody TelemetryDTO request) {
        telemetryService.saveTelemetry(request);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    
}
