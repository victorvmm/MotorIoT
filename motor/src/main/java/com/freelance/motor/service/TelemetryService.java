package com.freelance.motor.service;

import org.springframework.stereotype.Service;

import com.freelance.motor.repository.TelemetryRepository;

@Service
public class TelemetryService {
    private final TelemetryRepository teleRepo;

    public TelemetryService(TelemetryRepository teleRepo) {
        this.teleRepo = teleRepo;
    }

    
}
