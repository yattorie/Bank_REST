# 🏦 Bank Card Management System

---

A RESTful application for managing users, bank cards, transfers, and card blocking requests.  
Secure authentication using JWT is implemented, along with data validation, role-based access control, and protected operations.

---

## 🚀 Technologies and Stack

<details>
  <summary>🛠 <strong>Backend</strong></summary>

- **Java 17+**
- **Spring Boot**: Web, Data JPA, JDBC, Validation, Security
- **JWT**
- **MapStruct**
- **Liquibase** / **Preliquibase** — database migrations
- **Lombok**
- **PostgreSQL**
</details>

<details>
  <summary>🧪 <strong>Testing</strong></summary>

- **JUnit Jupiter**
- **Mockito**
</details>

<details>
  <summary>📄 <strong>Documentation</strong></summary>

- **Springdoc OpenAPI** + **Swagger UI**
</details>

<details>
  <summary>⚙️ <strong>Code Quality</strong></summary>

- **Checkstyle**
</details>

<details>
  <summary>📦 <strong>Build</strong></summary>

- **Maven**
</details>

<details>
  <summary>🐳 <strong>Containerization</strong></summary>

- **Docker** / **Docker Compose**
</details>

---

## 🔐 Authentication and Roles

| Role            | Capabilities                                                     |
|-----------------|------------------------------------------------------------------|
| `ROLE_USER`     | Access only to own data                                          |
| `ROLE_ADMIN`    | Full administrative access                                      |

- JWT authentication with access and refresh tokens

---

## 💡 Key Features

- 👥 **User registration and login**
- 🔐 **Token issuance and refresh**
- 💳 **Bank card management**: creation, activation, blocking, deletion
- 🔁 **Transfers between own cards**
- 🧾 **Submitting card blocking requests**
- 💸 **Card balance top-up**
- 📊 **Viewing current balance**
- 👤 **User management** (admin only)

---

## 📘 API Documentation

- Swagger UI: [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)
- Full OpenAPI spec: [`/docs/openapi.yaml`](docs/openapi.yaml)

---

## ⚙️ Project Startup Guide

```bash
# 1. Clone the repository
git clone https://github.com/yattorie/Bank_REST.git
cd Bank_REST

# 2. Create a .env file in the root of the project (example .env.example)

# 3. Build the project
mvn clean install

# 4. Start the application and dependencies
docker compose up -d
```
---

## 🧑‍💻 Contacts

- Автор: [yattorie](https://github.com/yattorie)
