package com.freelance.motor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TelemetryDTO (
    
    @Schema(description = "Unique truck identifier (Plate)", example = "AAA0A00")
    @NotBlank(message = "Truck identifier is required")
    String truckId,

    @Schema(description = "Temperature reading from AM2302 sensor in Celsius", example = "8.5")
    @NotNull(message = "Temperature reading is required")
    Double temperature
){}
