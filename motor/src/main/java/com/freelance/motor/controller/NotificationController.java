package com.freelance.motor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelance.motor.dto.NotificationDTO;
import com.freelance.motor.entity.Messages;
import com.freelance.motor.service.MessageService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final MessageService messageService;

    public NotificationController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Messages> receiveNotification(@Valid @RequestBody NotificationDTO request) {
        messageService.registerNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
}
