# MotorIoT
Motor de notificações via serviço de mensageria + IoT

To-do list:
 - Directories:
    * config: configuration classes (RabbitMQ connections, resilience configs)
    * controller: Notification & telemetry controllers
    * dto: data transfer objects
    * exception: global error handling
    * repository: JPA interfaces 
    * service: services (queue data, telemetry rule)
    * worker: queue messages, perform HTTP calls

 - Create docker-compose.yml for database
 - Create enums for message status (PENDING, SENT, FAILED)
 - Create DTO
 - Create notifications and telemetry endpoints
 - Add RabbitMQ to compose
 - Implement worker
 - Learn about DLQ and implement
 - Create IoT layer
