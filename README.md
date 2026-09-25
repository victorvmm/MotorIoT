# 🚚 Multichannel Notification Engine & IoT Telemetry

An **Event-Driven** system built with **Java 25** and **Spring Boot** designed for high-resilience asynchronous notification processing and real-time refrigerated fleet telemetry monitoring.

---

## 🏗 System Architecture & Flow

```text
 [ ESP32 Sensor / HTTP Client ] 
               │
               ▼
   ┌───────────────────────┐
   │  REST API (Spring)    │ ───► HTTP Status 202 (Accepted)
   └───────────┬───────────┘
               │
      ┌────────┴────────┐
      ▼                 ▼
┌────────────┐   ┌─────────────┐
│ PostgreSQL │   │  RabbitMQ   │
└────────────┘   └──────┬──────┘
                        │ (Async Consumption)
                        ▼
               ┌────────────────┐
               │ Worker Service │
               └────────┬───────┘
                        │
                        ▼
            [ External Provider ]
             (AWS SES / Twilio)
         

The System Flow
 - Reception: The API (/api/v1/notifications) receives a POST request from another company system requesting the dispatch of a message.
 - Initial Persistence: The system saves the record in the database (PostgreSQL) with the status PENDING.
 - Queuing: The system sends the data to a queue in RabbitMQ (e.g., email_queue).   
 - Fast Response: The API returns an HTTP 202 (Accepted) status to the caller, freeing the client immediately.
 
⚙️ Core Component: The Worker
 The Worker (or listener) is considered the asynchronous heart of the project.
 - It is a component that runs in the background and is responsible for processing the sending of messages without overloading the network or the main application system.
 - Message Consumption: Using the @RabbitListener annotation, the Worker listens to and consumes messages posted in the RabbitMQ queue.
 - Content Preparation: It joins the template column (the shell of the notification) with the variables column (dynamic data like a truck's license plate and temperature) to fill in the blanks of the final message.
 - External HTTP Integration: The Worker makes the actual HTTP call to provider APIs (like SendGrid, AWS SES, or Twilio) using tools like Spring WebClient or RestTemplate.
 - Success Handling: If the third-party API processes and delivers the message perfectly, the Worker updates the record history in the PostgreSQL database to the SENT status.
 - Resilience & Fault Tolerance: If there is network unavailability or the external API fails, the system enters a retry mechanism (re-sending attempts with progressive intervals) managed by Spring Retry or Resilience4j.
 - Error Queuing (DLQ): If retry attempts are definitively exhausted, the message is moved to a Dead Letter Queue (DLQ) in RabbitMQ to avoid blocking subsequent sends. When this occurs, the status in the database is updated to FAILED.

🛠 Tech Stack
 - Language: Java 25
 - Framework: Spring Boot 4 (Spring Web, Spring Data JPA, Validation).
 - Messaging: RabbitMQ via Spring AMQP.
 - Database: PostgreSQL.
 - Infrastructure: Docker and Docker Compose.
 - Integrations: Spring WebClient.   
 - Resilience: Spring Retry.   
 - Email Integration: AWS SES via AWS SDK.
 
📂 Project StructureThe application is modularized into the following folder structure:
motor-notificacoes/
├── docker-compose.yml
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── freelance/
        │           └── notificacoes/
        │               ├── MotorNotificacoesApplication.java
        │               ├── config/
        │               ├── controller/
        │               ├── dto/
        │               ├── entity/
        │               ├── exception/
        │               ├── repository/
        │               ├── service/
        │               └── worker/
        └── resources/
            └── application.properties


Layer Details:
 - config/: Folder for system configuration classes. Here you define RabbitMQ connection settings (like Exchanges and Queues) via Spring AMQP and resilience configurations, such as retry policies using Spring Retry or Resilience4j.
 - controller/: The Web layer of the application where classes annotated with @RestController reside. It includes the NotificationController responding on the /api/v1/notifications endpoint returning HTTP 202 (Accepted) instantly. It also includes the ingestion endpoint (e.g., /api/v1/telemetry) responsible for receiving regular POST requests sent by the truck.
 - dto/: Data transfer objects layer, like the NotificationRequestDTO. It is essential for applying request validations (via spring-boot-starter-validation) and ensuring critical fields are not blank.
 - entity/: Where classes annotated with @Entity reside for relational mapping. It includes the entity representing the notifications table (with the variables column supporting dynamic data) and the entity representing the telemetry table. It also contains the Enums that control the sending status in the database (PENDING, SENT, FAILED).
 - exception/: Folder dedicated to centralization and global error handling of your API, using the @ControllerAdvice annotation.
 - repository/: Interfaces that extend Spring Data JPA, responsible for direct communication with the PostgreSQL database.
 - service/: Contains the main business logic, like the NotificationService. This is where the system saves the record with PENDING status, publishes data to the RabbitMQ queue, and evaluates the telemetry rule (checking if the temperature passed the limit before generating an alert).
 - worker/: The asynchronous heart of the project. Here lies the component with the @RabbitListener annotation that consumes queue messages. This Worker runs in the background and makes the real HTTP call (via Spring WebClient or RestTemplate) to the external API.
