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

# 2. Create a .env file in the project root

# 3. Build the project
mvn clean install

# 4. Start the application and dependencies
docker compose up -d
```

#### Example .env file

```env
HOST=localhost
POSTGRES_USERNAME=postgres
POSTGRES_PASSWORD=root
POSTGRES_DATABASE=banking
POSTGRES_SCHEMA=banking

JWT_SECRET=71472e78c8154ede3e98ffcf9bcca01b8d2299d5d9457ef147b82914d1f02b8e9f524241d9ad50dea9f4cb34cafddceb3319872fd5b56ff2f0f0ac36d6f4fb21
ACCESS_TOKEN_EXPIRATION=36000000
REFRESH_TOKEN_EXPIRATION=252000000

ENCRYPTION_PASSWORD=MYSECRETPASSWORD
ENCRYPTION_SALT=1234567890ABCDEF
ENCRYPTION_KEY=h6X0Zk0oyk0nVxN5d8AQjA==
```

---

## 🧑‍💻 Contacts

- Автор: [yattorie](https://github.com/yattorie)