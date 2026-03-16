# Online Shopping Cart System

Production-ready full-stack e-commerce application built with Spring Boot, MongoDB, React, and JWT authentication.

## Project Structure

```text
ProjectRoot/
├── backend/
│   ├── docs/
│   ├── postman/
│   ├── src/
│   ├── pom.xml
│   └── .env.example
├── frontend/
│   ├── public/
│   ├── src/
│   ├── package.json
│   └── .env.example
└── README.md
```

## Features

- Product catalog with search, category filters, product details, and inventory-aware cards
- Persistent MongoDB-backed shopping cart with quantity updates and Stream-based totals
- Checkout flow with async order processing using `CompletableFuture`
- Order history and order details pages
- JWT authentication with register/login and role-based admin APIs
- Admin dashboard for product CRUD and inventory management

## Core Java Concepts Included

- OOP through abstract base entities, interfaces, DTOs, service abstractions, and mappers
- Collections, Streams API, lambda expressions, enums, generics, and exception handling
- Repository pattern, service layer pattern, DTO pattern, and dependency injection
- Serialization support in domain models and async processing with multithreading

## Backend Setup

1. Open [backend/.env.example](/C:/Users/suruh/OneDrive/Desktop/ShoppingCart/backend/.env.example) and copy the values into your environment or IDE run configuration.
2. Add your MongoDB URL to `MONGO_URI`.
3. Run:

```bash
cd backend
mvn spring-boot:run
```

Backend runs at `http://localhost:8081`.

Default seeded admin:

- Email: `admin@novacart.com`
- Password: `Admin@123`

## Frontend Setup

1. Open [frontend/.env.example](/C:/Users/suruh/OneDrive/Desktop/ShoppingCart/frontend/.env.example) and set `REACT_APP_API_BASE_URL` if needed.
2. Run:

```bash
cd frontend
npm install
npm start
```

Frontend runs at `http://localhost:3000`.

## MongoDB URL

Set `MONGO_URI` in your backend environment file or terminal before starting the server.