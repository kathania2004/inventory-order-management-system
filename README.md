# Inventory & Order Management System

A backend REST API built with Java, Spring Boot, Spring Data JPA, Hibernate and MySQL.

## Features

- Category management
- Product CRUD
- Product search and category filtering
- Customer management
- Inventory tracking
- Low-stock detection
- Order creation with multiple products
- Automatic stock deduction when an order is placed
- Insufficient-stock validation
- Order cancellation with automatic stock restoration
- Duplicate-product validation within an order
- DTO-based API contracts
- Bean Validation
- Global exception handling
- Transactional order processing
- Pessimistic locking for inventory updates
- Postman-friendly REST APIs

## Architecture

```text
Client / Postman
       |
       v
Controller
       |
       v
Service
       |
       v
Repository
       |
       v
MySQL
```

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL 8+
- Maven
- Docker Compose
- Postman

## Database Relationships

```text
Category 1 ---- * Product
Product  1 ---- 1 Inventory
Customer 1 ---- * Order
Order    1 ---- * OrderItem
Product  1 ---- * OrderItem
```

`OrderItem` acts as the association between orders and products and stores the purchase-time unit price.

## Run with Docker

### 1. Start MySQL

```bash
docker compose up -d
```

### 2. Run the application

```bash
mvn spring-boot:run
```

The API starts on:

```text
http://localhost:8080
```

Or build and run the JAR:

```bash
mvn clean package
java -jar target/inventory-order-management-1.0.0.jar
```

## Run without Docker

Create a MySQL database:

```sql
CREATE DATABASE inventory_db;
```

Then configure:

```text
DB_URL=jdbc:mysql://localhost:3306/inventory_db
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

## API Endpoints

### Categories

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/categories` | Create category |
| GET | `/api/categories` | List categories |

### Products

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/products` | Create product |
| GET | `/api/products` | List products |
| GET | `/api/products/{id}` | Get product |
| GET | `/api/products?name=laptop` | Search by name |
| GET | `/api/products?categoryId=1` | Filter by category |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |

### Customers

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/customers` | Create customer |
| GET | `/api/customers` | List customers |

### Inventory

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/inventory` | List inventory |
| GET | `/api/inventory/{productId}` | Get product stock |
| PUT | `/api/inventory/{productId}` | Update stock |

### Orders

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/orders` | Place order |
| GET | `/api/orders` | List orders |
| GET | `/api/orders/{id}` | Get order |
| GET | `/api/orders/customer/{customerId}` | Customer order history |
| PUT | `/api/orders/{id}/cancel` | Cancel order |

## Example API Flow

### 1. Create category

```http
POST /api/categories
Content-Type: application/json
```

```json
{
  "name": "Electronics",
  "description": "Electronic devices and accessories"
}
```

### 2. Create product

```http
POST /api/products
Content-Type: application/json
```

```json
{
  "name": "Laptop",
  "description": "Business laptop",
  "price": 60000,
  "categoryId": 1
}
```

A new inventory record is automatically created with quantity `0`.

### 3. Add stock

```http
PUT /api/inventory/1
Content-Type: application/json
```

```json
{
  "quantity": 10,
  "reorderLevel": 3
}
```

### 4. Create customer

```http
POST /api/customers
Content-Type: application/json
```

```json
{
  "name": "Rahul Sharma",
  "email": "rahul@example.com",
  "phone": "9876543210"
}
```

### 5. Place order

```http
POST /api/orders
Content-Type: application/json
```

```json
{
  "customerId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

The backend calculates:

```text
60000 x 2 = 120000
```

and automatically changes stock:

```text
Before: 10
After:   8
```

### 6. Cancel order

```http
PUT /api/orders/1/cancel
```

Stock is restored:

```text
Before cancellation: 8
After cancellation:  10
```

## Business Rules

1. Product price must be greater than zero.
2. Inventory quantity cannot be negative.
3. A customer email must be unique.
4. Category names must be unique.
5. An order must contain at least one item.
6. The same product cannot appear twice in one order.
7. An order cannot exceed available inventory.
8. The order total is calculated by the backend.
9. Cancelling an order restores its reserved stock.
10. An already-cancelled order cannot be cancelled again.
11. Inventory rows are locked during order/cancellation stock updates to reduce race conditions.

## Project Structure

```text
src/main/java/com/himanshu/inventory
├── controller
├── dto
├── entity
├── exception
├── repository
└── service
```

## Future Enhancements

- Spring Security + JWT authentication
- Admin and customer roles
- Pagination and sorting
- Swagger / OpenAPI documentation
- Product image support
- Order status workflow
- Payment integration
- Redis caching
- Kafka event publishing
- Dockerize the Spring Boot application
- CI/CD with GitHub Actions
- AWS deployment

## Resume Description

**Inventory & Order Management System | Java, Spring Boot, Spring Data JPA, MySQL, Maven, REST API**

- Developed a RESTful backend application to manage products, categories, inventory, customers, and orders.
- Implemented CRUD operations using Spring Boot, Spring Data JPA, Hibernate, and MySQL.
- Designed a layered architecture using Controller, Service, Repository, DTO, and Entity layers.
- Implemented order processing with stock validation, automatic inventory deduction, order cancellation, and stock restoration.
- Implemented input validation, global exception handling, transactions, and REST API testing with Postman.
