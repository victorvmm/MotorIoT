package com.freelance.motor.worker;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.freelance.motor.config.RabbitMQConfig;
import com.freelance.motor.entity.Messages;
import com.freelance.motor.entity.StatusEnum;
import com.freelance.motor.repository.MessageRepository;

@Component
public class RabbitMQWorker {
    private final MessageRepository messageRepository;

    public RabbitMQWorker(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RabbitListener(queues=RabbitMQConfig.QUEUE_NAME)
    public void processNotification(Messages message){
        System.out.println("Processing notification - ID: " + message.getId() + " to " + message.getChannel() + " channel.");
        try {
            Thread.sleep(2000);
            message.setStatusMsg(StatusEnum.SENT);
            messageRepository.save(message);
            System.out.println("Message " + message.getId() + " sent successfully.");
        } catch (Exception e){
            message.setStatusMsg(StatusEnum.FAILED);
            messageRepository.save(message);
            System.err.println("Notification sending failed " + message.getId());
        }
    }
}
