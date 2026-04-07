# Task Tracker Microservice API

A RESTful microservice for managing task lists and tasks with session-based authentication.

---

## Table of Contents

- [Base URL](#base-url)
- [Authentication](#authentication)
- [Session Management](#session-management)
- [Task Lists](#task-lists)
- [Tasks](#tasks)
- [Error Handling](#error-handling)
- [Status Codes](#status-codes)

---

## Base URL

```
http://localhost:8080/api/v1
```

---

## Authentication

This API uses cookie-based session management. All endpoints except `POST /auth/login` and `POST /auth/register` require an active session. The session cookie is automatically set on successful login and must be included in subsequent requests.

- Cookie name: `session_id`
- Sessions expire after 24 hours of inactivity
- Only one active session per user is maintained at a time

---

## Session Management

### Register

Create a new user account.

```
POST /auth/register
```

**Request Body**

```json
{
  "username": "string",
  "password": "string"
}
```

**Response** `201 Created`

```json
{
  "id": "string",
  "username": "string",
  "created_at": "2024-01-01T00:00:00Z"
}
```

---

### Login

Authenticate and start a session. On success, a `Set-Cookie` header is returned with the session cookie.

```
POST /auth/login
```

**Request Body**

```json
{
  "username": "string",
  "password": "string"
}
```

**Response** `200 OK`

```
Set-Cookie: session_id=<token>; HttpOnly; Path=/; SameSite=Strict
```

```json
{
  "message": "Login successful",
  "user_id": "string"
}
```

---

### Logout

Invalidate the current session and clear the session cookie.

```
POST /auth/logout
```

**Headers**

```
Cookie: session_id=<token>
```

**Response** `200 OK`

```json
{
  "message": "Logged out successfully"
}
```

---

### Get Current Session

Return information about the currently authenticated user.

```
GET /auth/me
```

**Headers**

```
Cookie: session_id=<token>
```

**Response** `200 OK`

```json
{
  "id": "string",
  "username": "string",
  "created_at": "2024-01-01T00:00:00Z"
}
```

---

## Task Lists

All task list endpoints require an active session cookie.

### List All Task Lists

Return all task lists belonging to the authenticated user.

```
GET /lists
```

**Headers**

```
Cookie: session_id=<token>
```

**Response** `200 OK`

```json
[
  {
    "id": "string",
    "name": "string",
    "description": "string",
    "task_count": 0,
    "created_at": "2024-01-01T00:00:00Z",
    "updated_at": "2024-01-01T00:00:00Z"
  }
]
```

---

### Get a Task List

Return a single task list by ID.

```
GET /lists/{list_id}
```

**Path Parameters**

| Parameter | Type   | Description            |
|-----------|--------|------------------------|
| list_id   | string | The task list's unique ID |

**Headers**

```
Cookie: session_id=<token>
```

**Response** `200 OK`

```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "task_count": 0,
  "created_at": "2024-01-01T00:00:00Z",
  "updated_at": "2024-01-01T00:00:00Z"
}
```

---

### Create a Task List

Create a new task list for the authenticated user.

```
POST /lists
```

**Headers**

```
Cookie: session_id=<token>
Content-Type: application/json
```

**Request Body**

```json
{
  "name": "string",
  "description": "string"
}
```

| Field       | Type   | Required | Description                   |
|-------------|--------|----------|-------------------------------|
| name        | string | Yes      | Name of the task list         |
| description | string | No       | Optional description          |

**Response** `201 Created`

```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "task_count": 0,
  "created_at": "2024-01-01T00:00:00Z",
  "updated_at": "2024-01-01T00:00:00Z"
}
```

---

### Delete a Task List

Delete a task list and all tasks within it. This action is irreversible.

```
DELETE /lists/{list_id}
```

**Path Parameters**

| Parameter | Type   | Description               |
|-----------|--------|---------------------------|
| list_id   | string | The task list's unique ID |

**Headers**

```
Cookie: session_id=<token>
```

**Response** `204 No Content`

---

## Tasks

All task endpoints require an active session cookie. Tasks are always scoped to a parent task list.

### List All Tasks in a Task List

Return all tasks belonging to the specified task list.

```
GET /lists/{list_id}/tasks
```

**Path Parameters**

| Parameter | Type   | Description               |
|-----------|--------|---------------------------|
| list_id   | string | The task list's unique ID |

**Query Parameters**

| Parameter | Type   | Default | Description                                    |
|-----------|--------|---------|------------------------------------------------|
| status    | string | all     | Filter by status: `pending`, `done`, `all`     |
| page      | int    | 1       | Page number for pagination                     |
| limit     | int    | 20      | Number of results per page (max 100)           |

**Headers**

```
Cookie: session_id=<token>
```

**Response** `200 OK`

```json
{
  "data": [
    {
      "id": "string",
      "list_id": "string",
      "title": "string",
      "description": "string",
      "status": "pending",
      "due_date": "2024-01-01T00:00:00Z",
      "created_at": "2024-01-01T00:00:00Z",
      "updated_at": "2024-01-01T00:00:00Z"
    }
  ],
  "total": 0,
  "page": 1,
  "limit": 20
}
```

---

### Get a Task

Return a single task by ID.

```
GET /lists/{list_id}/tasks/{task_id}
```

**Path Parameters**

| Parameter | Type   | Description               |
|-----------|--------|---------------------------|
| list_id   | string | The task list's unique ID |
| task_id   | string | The task's unique ID      |

**Headers**

```
Cookie: session_id=<token>
```

**Response** `200 OK`

```json
{
  "id": "string",
  "list_id": "string",
  "title": "string",
  "description": "string",
  "status": "pending",
  "due_date": "2024-01-01T00:00:00Z",
  "created_at": "2024-01-01T00:00:00Z",
  "updated_at": "2024-01-01T00:00:00Z"
}
```

---

### Create a Task

Create a new task under the specified task list.

```
POST /lists/{list_id}/tasks
```

**Path Parameters**

| Parameter | Type   | Description               |
|-----------|--------|---------------------------|
| list_id   | string | The task list's unique ID |

**Headers**

```
Cookie: session_id=<token>
Content-Type: application/json
```

**Request Body**

```json
{
  "title": "string",
  "description": "string",
  "due_date": "2024-01-01T00:00:00Z"
}
```

| Field       | Type        | Required | Description                          |
|-------------|-------------|----------|--------------------------------------|
| title       | string      | Yes      | Title of the task                    |
| description | string      | No       | Optional details about the task      |
| due_date    | ISO 8601    | No       | Optional due date and time (UTC)     |

**Response** `201 Created`

```json
{
  "id": "string",
  "list_id": "string",
  "title": "string",
  "description": "string",
  "status": "pending",
  "due_date": "2024-01-01T00:00:00Z",
  "created_at": "2024-01-01T00:00:00Z",
  "updated_at": "2024-01-01T00:00:00Z"
}
```

---

### Update a Task

Update the fields of an existing task. Only the fields provided in the request body are updated.

```
PATCH /lists/{list_id}/tasks/{task_id}
```

**Path Parameters**

| Parameter | Type   | Description               |
|-----------|--------|---------------------------|
| list_id   | string | The task list's unique ID |
| task_id   | string | The task's unique ID      |

**Headers**

```
Cookie: session_id=<token>
Content-Type: application/json
```

**Request Body**

```json
{
  "title": "string",
  "description": "string",
  "status": "done",
  "due_date": "2024-01-01T00:00:00Z"
}
```

| Field       | Type        | Allowed Values          | Description                       |
|-------------|-------------|-------------------------|-----------------------------------|
| title       | string      |                         | Updated title                     |
| description | string      |                         | Updated description               |
| status      | string      | `pending`, `done`       | Updated status                    |
| due_date    | ISO 8601    |                         | Updated due date (UTC)            |

**Response** `200 OK`

```json
{
  "id": "string",
  "list_id": "string",
  "title": "string",
  "description": "string",
  "status": "done",
  "due_date": "2024-01-01T00:00:00Z",
  "created_at": "2024-01-01T00:00:00Z",
  "updated_at": "2024-01-01T00:00:00Z"
}
```

---

### Delete a Task

Delete a task by ID. This action is irreversible.

```
DELETE /lists/{list_id}/tasks/{task_id}
```

**Path Parameters**

| Parameter | Type   | Description               |
|-----------|--------|---------------------------|
| list_id   | string | The task list's unique ID |
| task_id   | string | The task's unique ID      |

**Headers**

```
Cookie: session_id=<token>
```

**Response** `204 No Content`

---

## Error Handling

All errors return a consistent JSON structure:

```json
{
  "error": {
    "code": "string",
    "message": "string"
  }
}
```

**Example**

```json
{
  "error": {
    "code": "NOT_FOUND",
    "message": "Task list with id 'abc123' does not exist"
  }
}
```

---

## Status Codes

| Code | Meaning                                                          |
|------|------------------------------------------------------------------|
| 200  | OK - Request succeeded                                           |
| 201  | Created - Resource was successfully created                      |
| 204  | No Content - Resource was successfully deleted                   |
| 400  | Bad Request - Malformed request body or missing required fields  |
| 401  | Unauthorized - No active session or session expired              |
| 403  | Forbidden - Authenticated user does not own the resource         |
| 404  | Not Found - Resource does not exist                              |
| 409  | Conflict - A resource with the same unique fields already exists |
| 500  | Internal Server Error - Unexpected server-side failure           |

---

## Notes

- All timestamps are returned in ISO 8601 format (UTC).
- Deleting a task list will permanently delete all tasks within it.
- A user can only access task lists and tasks that they own. Attempting to access another user's resources returns `403 Forbidden`.
- Sessions are invalidated on logout or after 24 hours of inactivity. After expiry, clients must log in again to obtain a new session cookie.
