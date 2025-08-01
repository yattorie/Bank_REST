# Документация

## Обзор
API предоставляет функционал для:
- Регистрации и авторизации пользователей
- Управления банковскими картами
- Переводов между картами
- Запросов на блокировку карт
- Управления пользователями

## Основные конечные точки

### Регистрация и авторизация 
| Метод | Путь                       | Права доступа      | Описание                           |
|-------|----------------------------|--------------------|------------------------------------|
| POST  | /api/v1/auth/register      | Без аутентификации | Регистрация нового пользователя    |
| POST  | /api/v1/auth/login         | Без аутентификации | Аутентификация и получение токенов |
| POST  | /api/v1/auth/refresh-token | Без аутентификации | Обновление access-токена           |

### Управление картами 
| Метод  | Путь                             | Права доступа  | Описание                              |
|--------|----------------------------------|----------------|---------------------------------------|
| GET    | /api/v1/cards                    | ADMIN          | Получить все карты                    |
| GET    | /api/v1/cards/{id}               | ADMIN          | Получить карту по ID                  |
| DELETE | /api/v1/cards/{id}               | ADMIN          | Удалить карту                         |
| POST	  | /api/v1/cards/{id}/block         | ADMIN          | Заблокировать карту                   |
| POST   | /api/v1/cards/{id}/activate      | ADMIN          | Активировать карту                    |
| POST   | /api/v1/cards                    | ADMIN          | Создать новую карту                   |
| POST   | /api/v1/cards/{id}/request-block | USER           | Запросить  блокировку карты           |
| POST   | /api/v1/cards/{id}/deposit       | USER           | Пополнить баланс карты                |
| GET    | /api/v1/cards/{id}/balance       | USER           | Просмотр баланса карты                |
| GET    | /api/v1/cards/my                 | USER           | Получить карты текущего пользователя  |
| PUT    | /api/v1/cards/{id}               | ADMIN          | Обновить данные карты	                |

### Переводы между картами
| Метод | Путь              | Права доступа | Описание                     |
|-------|-------------------|---------------|------------------------------|
| POST  | /api/v1/transfers | USER          | Перевод между своими картами |

### Запросы на блокировку карт 
| Метод | Путь                                | Права доступа | Описание                           |
|-------|-------------------------------------|---------------|------------------------------------|
| GET   | /api/v1/block-requests              | ADMIN         | Получить все запросы на блокировку |
| POST  | /api/v1/block-requests/{id}/approve | ADMIN         | Одобрить запрос на блокировку      |
| POST  | /api/v1/block-requests/{id}/reject  | ADMIN         | Оклонить запрос на блокировку      |

### Управление пользователями
| Метод  | Путь                              | Права доступа | Описание                       |
|--------|-----------------------------------|---------------|--------------------------------|
| GET    | /api/v1/users                     | ADMIN         | Получить список пользователей  |
| GET    | /api/v1/users/{id}                | ADMIN         | Получить пользователя по ID    |
| GET    | /api/v1/users/username/{username} | ADMIN         | Получить пользователя по имени |
| GET    | /api/v1/users/email/{email}       | ADMIN         | Получить пользователя по email |
| POST   | /api/v1/users                     | ADMIN         | Создать нового пользователя    |
| PUT    | /api/v1/users/{id}                | ADMIN         | Обновить данные пользователя   |
| DELETE | /api/v1/users/{id}                | ADMIN         | Удалить пользователя           |


