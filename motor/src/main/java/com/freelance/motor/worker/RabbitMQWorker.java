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
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.Body;
import software.amazon.awssdk.services.sesv2.model.Content;
import software.amazon.awssdk.services.sesv2.model.Destination;
import software.amazon.awssdk.services.sesv2.model.EmailContent;
import software.amazon.awssdk.services.sesv2.model.Message;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;

@Component
public class RabbitMQWorker {
    private final MessageRepository messageRepository;


    private final SesV2Client sesV2Client;

    @Value("${aws.ses.sender-email}")
    private String senderEmail;

    public RabbitMQWorker(MessageRepository messageRepository, SesV2Client client) {
        this.messageRepository = messageRepository;
        this.sesV2Client = client;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    @Retryable(
        retryFor = { SdkException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void processNotification(Messages message){
        if ("EMAIL".equalsIgnoreCase(message.getChannel())){
            sendEmailViaSES(message);
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

    private void sendEmailViaSES(Messages message){
        String emailSubject = "System alert - Template: " + message.getTemplate();
        String emailBody = "Notification processed. Data: " + message.getVariables();
        SendEmailRequest emailRequest = SendEmailRequest.builder()
            .fromEmailAddress(senderEmail)
            .destination(Destination.builder()
            .toAddresses(message.getRecipient())
                        .build())
                .content(EmailContent.builder()
                        .simple(Message.builder()
                                .subject(Content.builder().data(emailSubject).build())
                                .body(Body.builder()
                                        .text(Content.builder().data(emailBody).build())
                                        .build())
                                .build())
                        .build())
                .build();

        // Dispara o e-mail pela API do SES
        sesV2Client.sendEmail(emailRequest);
    }
}
