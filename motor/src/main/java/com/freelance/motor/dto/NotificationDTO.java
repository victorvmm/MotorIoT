package com.freelance.motor.dto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationDTO (
    @NotBlank(message="Notification channel required.")
    String channel,

    @NotBlank(message="Recipient required.")
    String recipient,

    @NotBlank(message="Message template required.")
    String template,

    @NotNull(message="Dynamic variables map required.")
    Map<String, Object> variables

){}