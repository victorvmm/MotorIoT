package com.freelance.motor.worker;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.freelance.motor.config.RabbitMQConfig;
import com.freelance.motor.entity.Messages;
import com.freelance.motor.entity.StatusEnum;
import com.freelance.motor.repository.MessageRepository;

@Component
public class RabbitMQWorker {
    private final MessageRepository messageRepository;
    private final RestTemplate restTemplate;

    public RabbitMQWorker(MessageRepository messageRepository, RestTemplate restTemplate) {
        this.messageRepository = messageRepository;
        this.restTemplate = restTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    @Retryable(
        retryFor = { RestClientException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void processNotification(Messages message){
        String dummyUrl = "http://fake-api.com/send";
        restTemplate.getForObject(dummyUrl, String.class);

        message.setStatusMsg(StatusEnum.SENT);
        messageRepository.save(message);
        System.out.println("Message " + message.getId() + " sent successfully.");
        
    }

    @RabbitListener(queues = RabbitMQConfig.DLQ_NAME)
    public void processFailedNotification(Messages failedMessage){
        System.err.println("Alert: notification " + failedMessage.getId() + " ran out of sending attempts and got added in DLQ.");
        failedMessage.setStatusMsg(StatusEnum.FAILED);
        messageRepository.save(failedMessage);
        System.out.println("Notification status updated: FAILED");
    }
}
