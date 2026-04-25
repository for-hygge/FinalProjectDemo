# Order Payment System
This project is a full-stack microservices system for managing orders and payments.

## Architecture
```
Frontend (React)
   ↓
Order Service ←→ Payment Service
        ↓/↑           ↓/↑
       Kafka (Event-driven)
```

## Services
**Frontend** \
Tech: React \
Deployed via AWS Amplify \
-> [Frontend README](./order-payment-frontend/README.md)

**Order Service** \
Tech: Spring Boot + MySQL (RDS) \
Handles order lifecycle and state transitions \
Consumes Kafka events from Payment Service \
-> [Order Service README](./order-service/README.md)

**Payment Service** \
Tech: Spring Boot + MongoDB \
Handles payment processing and status \
Publishes Kafka events to Order Service \
-> [Payment Service README](./payment-service/README.md)
