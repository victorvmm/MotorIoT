package com.freelance.motor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry 
public class RabbitMQConfig {
    public static final String DLQ_NAME = "notification.dlq";
    public static final String DLX_NAME = "notification.dlx";

    public static final String QUEUE_NAME = "notification.queue";
    public static final String EXCHANGE = "notification.exchange";
    public static final String ROUTING_KEY = "notification.routing.key";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME, true); // lasting so if broker restarts, the queue doesnt
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(EXCHANGE);
    }
    
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean 
    public MessageConverter jsonMessageConverter(){
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public Queue dlq() {
        return new Queue(DLQ_NAME, true);
    }

    @Bean
    public DirectExchange dlx() {
        return new DirectExchange(DLX_NAME);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(dlq()).to(dlx()).with(DLQ_NAME);
    }
}
