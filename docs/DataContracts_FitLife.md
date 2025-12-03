# Data Contracts & API Specification

## 1. Base URL
`https://api.fitlife-automation.io/v1`

## 2. Authentication
**Request Headers:**
`Authorization: Bearer <token>` (Required for all non-auth endpoints)

### 2.1 Login
*   **Endpoint:** `POST /auth/login`
*   **Request:**
```json
{
  "email": "user@example.com",
  "password": "secret123"
}
```
*   **Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1...",
  "expires_in": 3600,
  "user": {
    "id": "u_123",
    "name": "Alex",
    "avatar_url": "https://cdn..."
  }
}
```

## 3. Workflow Management

### 3.1 Get Triggers
*   **Endpoint:** `GET /integrations/triggers`
*   **Response:**
```json
[
  {
    "id": "tr_slack_msg",
    "service_name": "Slack",
    "trigger_name": "New Message",
    "icon_url": ".../slack.png",
    "color_hex": "#4A154B"
  },
  {
    "id": "tr_gmail_rx",
    "service_name": "Gmail",
    "trigger_name": "New Email",
    "icon_url": ".../gmail.png",
    "color_hex": "#EA4335"
  }
]
```

### 3.2 Create Workflow
*   **Endpoint:** `POST /workflows`
*   **Request:**
```json
{
  "name": "Daily Standup",
  "trigger_id": "tr_slack_msg",
  "action_id": "act_jira_create",
  "is_active": true
}
```

### 3.3 Get History
*   **Endpoint:** `GET /workflows/history`
*   **Response:**
```json
[
  {
    "execution_id": "ex_999",
    "workflow_name": "Daily Standup",
    "status": "SUCCESS",
    "timestamp": "2025-10-26T14:30:00Z",
    "details": "Processed 1 item"
  }
]
```

## 4. Error Handling
Standardized Error Object:
```json
{
  "code": "VALIDATION_ERROR",
  "message": "Password is too short",
  "details": {}
}
```