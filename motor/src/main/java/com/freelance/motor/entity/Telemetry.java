package com.freelance.motor.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="telemetry")
public class Telemetry {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="truckId")
    private String truckId;

    @Column(name="temperature")
    private double temperature;

    @Column(name="checkedAt")
    private LocalDateTime checkedAt;

    public Telemetry(){}

    public Telemetry(String truckId, double temperature, LocalDateTime checkedAt) {
        this.truckId = truckId;
        this.temperature = temperature;
        this.checkedAt = checkedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(LocalDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }

    @Override
    public String toString() {
        return "Telemetry [id=" + id + ", truckId=" + truckId + ", temperature=" + temperature + ", checkedAt="
                + checkedAt + "]";
    }
}
