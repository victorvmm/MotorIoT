package com.freelance.motor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelance.motor.entity.Messages;
import com.freelance.motor.service.ReprocessService;

@RestController
@RequestMapping("/api/v1/notifications")
public class ReprocessController {

    private final ReprocessService reprocessService;

    public ReprocessController(ReprocessService reprocessService) {
        this.reprocessService = reprocessService;
    }

    // Endpoint: POST /api/v1/notifications/{id}/reprocess
    @PostMapping("/{id}/reprocess")
    public ResponseEntity<String> reprocessMessage(@PathVariable int id) {
        try {
            Messages reprocessedMessage = reprocessService.reprocessFailedMessage(id);
            return ResponseEntity.ok("Mensagem " + reprocessedMessage.getId() + " encaminhada para reprocessamento com sucesso.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            // Retorna 400 Bad Request se a mensagem não existir ou não estiver em FAILED
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Retorna 500 se o RabbitMQ estiver fora do ar no momento da republicação
            return ResponseEntity.internalServerError().body("Erro ao tentar reprocessar: " + e.getMessage());
        }
    }
}