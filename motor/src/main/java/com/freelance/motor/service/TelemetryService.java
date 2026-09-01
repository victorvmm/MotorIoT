package com.freelance.motor.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import com.freelance.motor.dto.NotificationDTO;
import com.freelance.motor.dto.TelemetryDTO;
import com.freelance.motor.entity.Telemetry;
import com.freelance.motor.repository.TelemetryRepository;

import jakarta.transaction.Transactional;

@Service
public class TelemetryService {
    private final TelemetryRepository teleRepo;
    private final double limit_temperature;

    public TelemetryService(TelemetryRepository teleRepo, double limit) {
        this.teleRepo = teleRepo;
        this.limit_temperature = limit;
    }

    @Transactional
    public Telemetry saveTelemetry(TelemetryDTO dto){

        Telemetry telemetry = new Telemetry(dto.truckId(), dto.temperature(), LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        if (telemetry.getTemperature() > limit_temperature){
            NotificationDTO notificationDTO = new NotificationDTO("SMS", null, null, null)
        }

        return teleRepo.save(telemetry);
    }
}
