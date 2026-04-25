# Introduction
This project is a dedicated microservice responsible for handling all payment-related operations (payment initiation, status tracking, interact with external payment providers).

The service is built using **Spring Boot** and follows an event-driven architecture using **Kafka**. Events are used for asynchronous communication between services. It leverages **MongoDB** for flexible storage of payment transactions and logs.

## Endpoints
GET
- ```/v1/payments/{paymentId}``` - retrieve a specific payment by paymentId

POST
- ```/v1/payments``` - create a payment 
- ```/v1/payments/{paymentId}/cancel``` - cancel a payment
- ```/v1/payments/{paymentId}/refund``` - refund a payment

## Setup
http://localhost:8081

## Kafka Flow
1. Receives payment API requests

2. Processes payment logic

3. Publishes Kafka events:
   payment-created   → sent to order-service
   payment-cancelled → sent to order-service
   payment-refunded  → sent to order-service

## Payment Status
```
PROCESSING → Payment is being processed
CANCELLED  → Payment cancelled
REFUNDED   → Payment refunded
```

## Swagger UI
Base URL: http://localhost:8081/swagger-ui.html

## Dependencies
- Kafka
- Lombok
- MongoDB
- Spring Security
- Swagger
