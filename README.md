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

## Future implementations:
 - Add exceptions
 - Create IoT layer
