package com.freelance.motor.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="telemetry")
@Getter
@Setter
public class Telemetry {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="truckId")
    private String truckId;

    @Column(name="temperature")
    private BigDecimal temperature;

    @Column(name="checkedAt")
    private LocalDateTime checkedAt;

    public Telemetry(){}

    public Telemetry(String truckId, BigDecimal temperature, LocalDateTime checkedAt) {
        this.truckId = truckId;
        this.temperature = temperature;
        this.checkedAt = checkedAt;
    }

    @Override
    public String toString() {
        return "Telemetry [id=" + id + ", truckId=" + truckId + ", temperature=" + temperature + ", checkedAt="
                + checkedAt + "]";
    }
}
