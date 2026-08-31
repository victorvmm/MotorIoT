package com.freelance.motor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TelemetryDTO (
    
    @NotBlank(message="Identificador do caminhão é obrigatório")
    String truckId,

    @NotNull(message="Leitura de temperatura obrigatória")
    Double temperature
){}
