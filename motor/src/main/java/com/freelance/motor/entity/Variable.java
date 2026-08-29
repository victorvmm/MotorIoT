package com.freelance.motor.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Variable {
    private String truckPlate;

    private BigDecimal temperature;

    public Variable(String truckPlate, BigDecimal temperature) {
        this.truckPlate = truckPlate;
        this.temperature = temperature;
    }
}
