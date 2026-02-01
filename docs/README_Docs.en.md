# Documentation

## Overview
The API provides functionality for:
- User registration and authentication
- Bank card management
- Card-to-card transfers
- Card blocking requests
- User management

## Main Endpoints

### Registration and Authentication
| Method | Path                       | Access Rights     | Description                     |
| ------ | -------------------------- | ----------------- | ------------------------------- |
| POST   | /api/v1/auth/register      | No authentication | Register a new user             |
| POST   | /api/v1/auth/login         | No authentication | Authenticate and receive tokens |
| POST   | /api/v1/auth/refresh-token | No authentication | Refresh access token            |

### Card Management
| Method | Path                             | Access Rights | Description                   |
| ------ | -------------------------------- | ------------- | ----------------------------- |
| GET    | /api/v1/cards                    | ADMIN         | Get all cards                 |
| GET    | /api/v1/cards/{id}               | ADMIN         | Get card by ID                |
| DELETE | /api/v1/cards/{id}               | ADMIN         | Delete card                   |
| POST   | /api/v1/cards/{id}/block         | ADMIN         | Block card                    |
| POST   | /api/v1/cards/{id}/activate      | ADMIN         | Activate card                 |
| POST   | /api/v1/cards                    | ADMIN         | Create a new card             |
| POST   | /api/v1/cards/{id}/request-block | USER          | Request card blocking         |
| POST   | /api/v1/cards/{id}/deposit       | USER          | Deposit funds to card balance |
| GET    | /api/v1/cards/{id}/balance       | USER          | View card balance             |
| GET    | /api/v1/cards/my                 | USER          | Get current user’s cards      |
| PUT    | /api/v1/cards/{id}               | ADMIN         | Update card details           |

### Card-to-Card Transfers
| Method | Path              | Access Rights | Description                |
| ------ | ----------------- | ------------- | -------------------------- |
| POST   | /api/v1/transfers | USER          | Transfer between own cards |

### Card Blocking Requests
| Method | Path                                | Access Rights | Description                    |
| ------ | ----------------------------------- | ------------- | ------------------------------ |
| GET    | /api/v1/block-requests              | ADMIN         | Get all card blocking requests |
| POST   | /api/v1/block-requests/{id}/approve | ADMIN         | Approve card blocking request  |
| POST   | /api/v1/block-requests/{id}/reject  | ADMIN         | Reject card blocking request   |

### User Management
| Method | Path                              | Access Rights | Description          |
| ------ | --------------------------------- | ------------- | -------------------- |
| GET    | /api/v1/users                     | ADMIN         | Get list of users    |
| GET    | /api/v1/users/{id}                | ADMIN         | Get user by ID       |
| GET    | /api/v1/users/username/{username} | ADMIN         | Get user by username |
| GET    | /api/v1/users/email/{email}       | ADMIN         | Get user by email    |
| POST   | /api/v1/users                     | ADMIN         | Create a new user    |
| PUT    | /api/v1/users/{id}                | ADMIN         | Update user details  |
| DELETE | /api/v1/users/{id}                | ADMIN         | Delete user          |



