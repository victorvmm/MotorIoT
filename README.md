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

## Current:


## Future:
 - Add exceptions
 - Add RabbitMQ to compose
 - Implement worker + update docker-compose with MQ
 - Learn about DLQ and implement
 - Create IoT layer
