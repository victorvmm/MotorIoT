package com.freelance.motor.worker;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import com.freelance.motor.config.RabbitMQConfig;
import com.freelance.motor.entity.Messages;
import com.freelance.motor.entity.StatusEnum;
import com.freelance.motor.repository.MessageRepository;

import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Component
public class RabbitMQWorker {
    private final MessageRepository messageRepository;
    private final SnsClient snsClient;

    @Value("${aws.sns.topic-arn}")
    private String topicArn;

    public RabbitMQWorker(MessageRepository messageRepository, SnsClient client) {
        this.messageRepository = messageRepository;
        this.snsClient = client;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    @Retryable(
        retryFor = { SdkException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void processNotification(Messages message){
        if ("SMS".equalsIgnoreCase(message.getChannel())){
            sendNotificationViaSNS(message);
        }

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

    private void sendNotificationViaSNS(Messages message){
        String subject = "System alert - Template: " + message.getTemplate();
        String messageBody = "Notification processed. Recipient: " + message.getRecipient() + ". Data: " + message.getVariables();
        
        PublishRequest publishRequest = PublishRequest.builder()
            .topicArn(topicArn)
            .subject(subject)
            .message(messageBody)
            .build();

        snsClient.publish(publishRequest);
    }
}
