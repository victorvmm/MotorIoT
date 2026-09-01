package com.freelance.motor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelance.motor.dto.NotificationDTO;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    
    @PostMapping
    public ResponseEntity<Void> receiveNotification(@Valid @RequestBody NotificationDTO request) {
        return ResponseEntity.accepted().build();
    }
    
}
