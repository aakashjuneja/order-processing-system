# Order Processing System

Java Spring Boot backend implementing an Order Processing System.

Features:
- Create order with multiple items
- Retrieve order by id
- Update order status
- Scheduled job that promotes PENDING orders to PROCESSING every 5 minutes
- List orders (optionally by status)
- Cancel order (only if PENDING)
- H2 in-memory database
- Swagger/OpenAPI UI available at /swagger-ui.html or /swagger-ui/index.html

Build:
- Requires Java 17+ and Gradle
- Build: `./gradlew clean build`
- Run: `./gradlew bootRun`

Tests:
- Unit and controller tests included. JaCoCo configured to generate coverage report.


# Order Processing System – README

## Overview
This backend system enables customers to place and manage orders, track status, and perform essential order operations. It follows SOLID design principles, layered architecture, and clean coding practices to ensure maintainability, scalability, and testability.

---

# 🔹 High-Level Design (HLD)

## Architecture
- **Presentation Layer**: REST Controllers (Spring Web)
- **Service Layer**: Business logic with interfaces & implementations
- **Data Access Layer**: Spring Data JPA repositories
- **Database**: H2 in-memory database
- **Schedulers**: Background job that updates PENDING → PROCESSING
- **APIs**: Exposed via Springdoc Swagger UI
- **Build Tool**: Gradle

---

# 🔹 Low-Level Design (LLD)

## Entities

### OrderEntity
- id (Long)
- customerName (String)
- createdAt (OffsetDateTime)
- status (Enum: PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED)
- items (List<OrderItem>)

### OrderItem
- id (Long)
- productName (String)
- price (BigDecimal)
- quantity (int)

---

# 🔹 Use Cases

### 1. Create an Order
### 2. Retrieve Order Details
### 3. List Orders
### 4. Update Order Status
### 5. Auto-Promotion Scheduler
### 6. Cancel Order

---

# 🔹 Flow Diagram

[Create Order] → [PENDING] → (Auto 5 min) → [PROCESSING] → [SHIPPED] → [DELIVERED]  
                             ↘ [CANCELLED]

---

# 🔹 Class UML Diagram

## Entities
OrderEntity → OrderItem (1-to-many)

## Service Layer
OrderService → OrderServiceImpl

## Controller
OrderController

---

# 🔹 API Endpoints

- POST `/api/orders`
- GET `/api/orders/{id}`
- GET `/api/orders?status=PENDING`
- PATCH `/api/orders/{id}/status?status=SHIPPED`
- POST `/api/orders/{id}/cancel`

---

# 🔹 AI Usage Summary

- **40% AI Assistance**
  - Helped structure sections
  - Generated boilerplate documentation
  - Improved formatting and flow
  - Helped ensure architectural clarity

- **60% Manual Contribution**
  - Business logic validation
  - Design decisions
  - Refining LLD/HLD
  - Ensuring documentation accuracy
  - Making decisions based on engineering experience

---

# 🔹 How to Run

1. Generate Gradle Wrapper:
   ```
   gradle wrapper
   ```
2. Build:
   ```
   ./gradlew clean build
   ```
3. Run:
   ```
   ./gradlew bootRun
   ```
4. Swagger:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

---

# End of Document

