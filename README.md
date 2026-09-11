# MotorIoT
Motor de notificações via serviço de mensageria + IoT


## Directories:
   * config: configuration classes (RabbitMQ connections, resilience configs)
   * controller: Notification & telemetry controllers
   * dto: data transfer objects
   * exception: global error handling
   * repository: JPA interfaces 
   * service: services (queue data, telemetry rule)
   * worker: queue messages, perform HTTP calls

## On-going:
 - Learn and implement RestTemplate

## To-do list:
 - Create SES url for email sending
 - Implement API receiving POST from ESP32 and sending to SES
 - Change db name
 - AWS deploy
