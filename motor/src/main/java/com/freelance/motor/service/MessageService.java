package com.freelance.motor.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.freelance.motor.config.RabbitMQConfig;
import com.freelance.motor.dto.NotificationDTO;
import com.freelance.motor.entity.Messages;
import com.freelance.motor.repository.MessageRepository;

import jakarta.transaction.Transactional;

@Service
public class MessageService {
    private final MessageRepository msgRepo;
    private final RabbitTemplate rabbitTemplate;

    public MessageService(MessageRepository msgRepo, RabbitTemplate rabbitTemplate) {
        this.msgRepo = msgRepo;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public void registerNotification(NotificationDTO dto){
        // Re-queue (?) message if failed or still pending
        Messages message = new Messages(
                dto.channel(), 
                dto.recipient(),
                dto.template(),
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))
        );
        Messages savedMessage = msgRepo.save(message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, savedMessage);
    }
}
