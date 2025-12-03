# Data Models & API Contracts

## 1. JSON Data Models

### User Object
```json
{
  "id": "uuid-v4",
  "email": "alex@example.com",
  "displayName": "Alex Dev",
  "avatarUrl": "https://.../img.jpg"
}
```

### Workflow Object
```json
{
  "id": "wf_123",
  "name": "Email to Slack",
  "isActive": true,
  "trigger": {
    "type": "EMAIL_RECEIVED",
    "config": { "provider": "GMAIL" }
  },
  "action": {
    "type": "SLACK_MESSAGE",
    "config": { "channel": "#general" }
  },
  "createdAt": "2025-01-01T12:00:00Z"
}
```

### ExecutionLog Object
```json
{
  "id": "log_999",
  "workflowId": "wf_123",
  "status": "SUCCESS", // or "FAILURE"
  "timestamp": "2025-01-02T08:30:00Z",
  "details": "Message sent successfully."
}
```

## 2. API Endpoints (REST)

### Authentication
*   **POST** `/api/v1/auth/login`
    *   **Request**: `{ "email": "...", "password": "..." }`
    *   **Response**: `{ "token": "jwt_token", "user": { ... } }`

### Automation
*   **GET** `/api/v1/triggers`
    *   Returns list of available triggers for the "Choose a Trigger" screen.
*   **GET** `/api/v1/actions`
    *   Returns list of available actions.
*   **POST** `/api/v1/workflows`
    *   **Request**: Payload matching Workflow Object structure (minus ID/timestamps).
    *   **Response**: 201 Created.

### History
*   **GET** `/api/v1/history`
    *   **Params**: `page=1`, `limit=20`.
    *   **Response**: `{ "data": [ExecutionLog], "nextPage": 2 }`

## 3. Error Codes
*   `AUTH_001`: Invalid Credentials.
*   `WF_005`: Trigger configuration invalid.
*   `SRV_500`: Internal Engine Error.