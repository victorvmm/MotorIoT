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
 - Create IoT layer

## To-do list:
 - Create SES url for email sending
 - Change db name
 - AWS deploy
