package com.freelance.motor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelance.motor.entity.Telemetry;


public interface TelemetryRepository extends JpaRepository<Telemetry, Integer> {
    Optional<Telemetry> findById(int id);

    boolean existsById(int id);
}
