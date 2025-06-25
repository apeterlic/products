# 🚀 Startup and Testing Guide

This guide outlines how to start and test the Java Spring Boot project using Docker Desktop,
Java 21, and supporting services.

---

## 🧰 Prerequisites

Make sure the following are installed on your system:

- Java 21
- Maven
- Docker Desktop
- Git

---

## 🐳 Start the Database with Docker

```bash
docker-compose -f docker/db-compose.yml up -d
```

**PostgreSQL Details:**

- **Image:** `postgres:15`
- **Container Name:** `beenary-products`
- **Username/Password:** `beenary`
- **Port Mapping:** `localhost:65432 → container:5432`
- **Volume:** `db_beenary_products` (data persisted)

Once the database is running, it is automatically **pre-populated** using Liquibase, which runs at
Spring Boot startup.

Liquibase changelogs are located at:

---

## 🚗 Run Spring Boot Application

From the root of the project:

```bash
./mvnw spring-boot:run
# or
mvn spring-boot:run
```

Make sure Docker services are running before starting the application.

---

## ✅ Testing the Application

### 1. API Endpoints

Use tools like Postman or cURL to test REST endpoints. For example:

```bash
curl http://localhost:8080/api/your-endpoint
```

You can use HTTP request provided inside the /examples directory to test the endpoints.

### 3. Database Verification

You can connect to the PostgreSQL database using tools like **DBeaver**, **pgAdmin**, or the CLI:

```bash
psql -h localhost -p 65432 -U beenary -d products
```

---

## 🧹 Cleanup

To stop and remove the containers:

```bash
docker compose -f db-compose.yml down
```

To also remove volumes:

```bash
docker compose -f db-compose.yml down -v
```

---

## 📄 Notes

- Make sure Docker Desktop is running before launching containers.
- If ports are already in use, adjust them in the `db-compose.yml` and Spring Boot config.

---
