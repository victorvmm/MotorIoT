package com.freelance.motor.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freelance.motor.config.RabbitMQConfig;
import com.freelance.motor.entity.Messages;
import com.freelance.motor.entity.StatusEnum;
import com.freelance.motor.repository.MessageRepository;

@Service
public class ReprocessService {

    private final MessageRepository messageRepository;
    private final RabbitTemplate rabbitTemplate;

    public ReprocessService(MessageRepository messageRepository, RabbitTemplate rabbitTemplate) {
        this.messageRepository = messageRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public Messages reprocessFailedMessage(int messageId) {
        // 1. Busca a mensagem no banco
        Messages message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Mensagem não encontrada com o ID: " + messageId));

        // 2. Valida se a mensagem realmente está com status de falha (na DLQ)
        if (!StatusEnum.FAILED.equals(message.getStatusMsg())) {
            throw new IllegalStateException("Apenas mensagens com status FAILED podem ser reprocessadas.");
        }

        // 3. Atualiza o status para PENDING (ou PROCESSING)
        message.setStatusMsg(StatusEnum.PENDING);
        messageRepository.save(message);

        // 4. Republica a mensagem na Exchange Principal (não na DLX/DLQ)
        // Isso fará com que ela caia na fila principal e o RabbitMQWorker tente enviá-la novamente
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE, 
                RabbitMQConfig.ROUTING_KEY, 
                message
        );

        return message;
    }
}