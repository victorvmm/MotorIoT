package com.freelance.motor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Multi-channel Notification Engine & IoT Telemetry")
                        .version("1.0.0")
                        .description("Event-Driven API for IoT telemetry data ingestion and asynchronous notification processing (E-mail, SMS, WhatsApp).")
                        .contact(new Contact()
                                .name("Victor Melo")
                                .email("victormmich@gmail.com"))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }
}