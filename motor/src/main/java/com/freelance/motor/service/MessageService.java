package com.freelance.motor.service;

import org.springframework.stereotype.Service;

import com.freelance.motor.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository msgRepo;

    public MessageService(MessageRepository msgRepo) {
        this.msgRepo = msgRepo;
    }

     @Transactional
     public Message 
}
