package com.freelance.motor.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.freelance.motor.dto.NotificationDTO;
import com.freelance.motor.dto.TelemetryDTO;
import com.freelance.motor.entity.Telemetry;
import com.freelance.motor.repository.TelemetryRepository;

import jakarta.transaction.Transactional;

@Service
public class TelemetryService {
    private final TelemetryRepository teleRepo;
    private final MessageService msgService;
    private final double limit_temperature;

    @Value("${email}")
    private String email;

    public TelemetryService(TelemetryRepository teleRepo, double limit, MessageService msgService) {
        this.teleRepo = teleRepo;
        this.limit_temperature = limit;
        this.msgService = msgService;
    }

    @Transactional
    public void saveTelemetry(TelemetryDTO dto){

        Telemetry telemetry = new Telemetry(dto.truckId(), dto.temperature(), LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        if (telemetry.getTemperature() > limit_temperature){
            HashMap<String,Double> map = new HashMap<>();
            map.put(telemetry.getTruckId(), telemetry.getTemperature());
            NotificationDTO notificationDTO = new NotificationDTO("EMAIL", email, "HIGH_TEMPERATURE_ALERT", map);
            msgService.registerNotification(notificationDTO);
        }

        teleRepo.save(telemetry);
    }
}
