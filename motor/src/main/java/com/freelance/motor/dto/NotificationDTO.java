package com.freelance.motor.dto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationDTO (
    @NotBlank(message="O canal de notificações é obrigatório.")
    String channel,

    @NotBlank(message="O destinatário é obrigatório")
    String recipient,

    @NotBlank(message="O template da mensagem é obrigatório")
    String template,

    @NotNull(message="Mapa de variáveis dinâmicas é obrigatório")
    Map<String, String> variables

){}