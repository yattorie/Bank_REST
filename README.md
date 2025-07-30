# 🏦 Система управления банковскими картами

---

RESTful-приложение для управления пользователями, банковскими картами, переводами и запросами на блокировку.  
Реализована безопасная аутентификация с помощью JWT, валидация данных, контроль доступа по ролям и защищённые операции.

---

## 🚀 Технологии и стек

<details>
  <summary>🛠 <strong>Backend</strong></summary>

- **Java 17+**
- **Spring Boot**: Web, Data JPA, JDBC, Validation, Security
- **JWT**
- **MapStruct**
- **Liquibase** / **Preliquibase** — миграции БД
- **Lombok**
- **PostgreSQL**
</details>

<details>
  <summary>🧪 <strong>Тестирование</strong></summary>

- **JUnit Jupiter**
- **Mockito**
</details>

<details>
  <summary>📄 <strong>Документация</strong></summary>

- **Springdoc OpenAPI** + **Swagger UI**
</details>

<details>
  <summary>⚙️ <strong>Качество кода</strong></summary>

- **Checkstyle**
</details>

<details>
  <summary>📦 <strong>Сборка</strong></summary>

- **Maven**
</details>

<details>
  <summary>🐳 <strong>Контейнеризация</strong></summary>

- **Docker** / **Docker Compose**
</details>

---

## 🔐 Аутентификация и роли

| Роль            | Возможности                                                         |
|-----------------|---------------------------------------------------------------------|
| `ROLE_USER`     | Доступ только к своим данным                                        |
| `ROLE_ADMIN`    | Полный административный доступ                                      |

- JWT-аутентификация с access и refresh токенами

---

## 💡 Основные возможности

- 👥 **Регистрация и вход пользователей**
- 🔐 **Получение и обновление токенов**
- 💳 **Управление банковскими картами**: создание, активация, блокировка, удаление
- 🔁 **Переводы между своими картами**
- 🧾 **Отправка запроса на блокировку карты**
- 💸 **Пополнение баланса карты**
- 📊 **Просмотр текущего баланса**
- 👤 **Управление пользователями** (для администратора)

---

## 📘 Документация API

- Swagger UI: [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)
- Полный OpenAPI: [`/docs/openapi.yaml`](docs/openapi.yaml)

---

## ⚙️ Инструкция запуска проекта

```bash
# 1. Клонировать репозиторий
git clone https://github.com/yattorie/Bank_REST.git
cd Bank_REST

# 2. Создать .env файл в корне проекта

# 3. Сборка проекта
mvn clean install

# 4. Запуск приложения и зависимостей
docker compose up -d
```

#### Пример файла .env

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

## 🧑‍💻 Контакты

- Автор: [yattorie](https://github.com/yattorie)
