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

    @PostMapping("/{id}/reprocess")
    public ResponseEntity<String> reprocessMessage(@PathVariable int id) {
        try {
            Messages reprocessedMessage = reprocessService.reprocessFailedMessage(id);
            return ResponseEntity.ok("Message " + reprocessedMessage.getId() + " submitted for reprocessing.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error trying to reprocess: " + e.getMessage());
        }
    }
}