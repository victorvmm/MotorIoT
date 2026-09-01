package com.freelance.motor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelance.motor.entity.Messages;

public interface MessageRepository extends JpaRepository<Messages, Integer>{
    Optional<Messages> findById(int id);

    boolean existsByTelemetryId(int id);
}
