# Introduction
This project is a core microservice responsible for managing the full lifecycle of customer orders (order creation, retrieval, cancellation, and state transitions).

The service is built using **Spring Boot** and follows an event-driven architecture by publishing and consuming domain events via **Kafka**. Events are used for asynchronous communication between services.

## Endpoints
GET
- ```/v1/orders？userId=``` - retrieve all orders by userId
- ```/v1/orders/{orderId}``` - retrieve a specific order by orderId
- ```/v1/orders/{orderId}/payment``` - retrieve payment details under a specific order

POST
- ```/v1/orders``` - create an order 
- ```/v1/orders/{orderId}/cancel``` - cancel an order 

## Setup
Base URL: http://localhost:8080

## Kafka Flow
1. Receives order API requests

2. Listens to Kafka events:
   payment-created   → update order with payment info
   payment-cancelled → update order status to cancelled
   payment-refunded  → update order status to refunded

3. Updates order data based on payment events

## Order Status
```
PENDING_PAYMENT   → Order created, waiting for payment
PAYMENT_CREATED   → Payment initiated
PAYMENT_CANCELLED → Payment cancelled
REFUNDED          → Payment refunded
CANCELLED         → Order manually cancelled
```

## Swagger UI
http://localhost:8080/swagger-ui.html

## Dependencies
- AWS RDS (MySQL)
- Kafka
- Lombok
- Spring Security
- Swagger
