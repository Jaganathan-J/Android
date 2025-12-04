# Data Models & API Contracts: FinanceManager

## 1. Overview
Communication is RESTful JSON over HTTPS. All requests require `Authorization: Bearer <token>` header.

## 2. Models

### 2.1 User
```json
{
  "id": "uuid-string",
  "email": "user@example.com",
  "fullName": "Alex User",
  "avatarUrl": "https://cdn.financemanager.com/avatars/1.png"
}
```

### 2.2 Integration (Items in Screen 4, 5)
```json
{
  "id": "slack_v2",
  "name": "Slack",
  "type": "ACTION", // or TRIGGER
  "iconUrl": "https://.../slack.png",
  "description": "Send messages to channels"
}
```

### 2.3 Workflow (The Automation Object)
```json
{
  "id": "wf_123",
  "name": "Invoice Bot 1",
  "trigger": {
    "integrationId": "gmail_v1",
    "event": "new_email_matching",
    "config": { "subject": "Invoice" }
  },
  "action": {
    "integrationId": "slack_v2",
    "event": "post_message",
    "config": { "channel": "#finance" }
  },
  "isActive": true,
  "createdAt": "2025-10-26T12:00:00Z"
}
```

## 3. API Endpoints

### 3.1 Authentication
*   `POST /auth/login`
    *   Request: `{ "email": "...", "password": "..." }`
    *   Response: `{ "accessToken": "...", "refreshToken": "...", "user": { ... } }`

### 3.2 Workflow Builder
*   `GET /integrations?type=TRIGGER`
    *   Returns List of Integration objects (Populates Screens 4 & 6).
*   `GET /integrations?type=ACTION`
    *   Returns List of Integration objects (Populates Screens 5 & 7).
*   `POST /workflows`
    *   Payload: Workflow Object (result of Screen 8).

### 3.3 History
*   `GET /workflows/{id}/history`
    *   Returns Paged list of Execution Logs (Populates Screen 9).
    *   Model: `{ "executionId": "...", "status": "SUCCESS", "timestamp": "...", "logMessage": "Sent to #finance" }`

## 4. Error Handling
All 4xx and 5xx responses follow a standard error envelope:
```json
{
  "code": "INVALID_TRIGGER_CONFIG",
  "message": "The selected Gmail trigger requires authentication.",
  "userFacingMessage": "Please reconnect your Gmail account."
}
```