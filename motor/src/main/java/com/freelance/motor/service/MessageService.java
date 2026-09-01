package com.freelance.motor.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import com.freelance.motor.dto.NotificationDTO;
import com.freelance.motor.entity.Messages;
import com.freelance.motor.entity.Telemetry;
import com.freelance.motor.repository.MessageRepository;

import jakarta.transaction.Transactional;

@Service
public class MessageService {
    private final MessageRepository msgRepo;

    public MessageService(MessageRepository msgRepo) {
        this.msgRepo = msgRepo;
    }

    @Transactional
    public Messages registerNotification(NotificationDTO dto, Telemetry telemetry){
        // Re-queue (?) message if failed or still pending
        Messages message = new Messages(
                dto.channel(), 
                dto.recipient(),
                dto.template(),
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))
        );
        message.setVariables(telemetry.getTruckId(), telemetry.getTemperature());
        return msgRepo.save(message);
    }
}
