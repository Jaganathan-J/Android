# Data Contracts & API Specification

### 1. Base Configuration
*   **Base URL:** `https://api.automationoverflow.com/v1/`
*   **Auth Header:** `Authorization: Bearer <access_token>`
*   **Content-Type:** `application/json`

### 2. Authentication Endpoints

#### POST /auth/login
*   **Request:**
```json
{
  "email": "user@example.com",
  "password": "Secr3tPa$$word"
}
```
*   **Response (200 OK):**
```json
{
  "accessToken": "ey...",
  "refreshToken": "rt...",
  "expiresIn": 3600,
  "user": {
    "id": "user_123",
    "name": "Alex",
    "avatarUrl": "https://cdn..."
  }
}
```

### 3. Workflow Endpoints

#### GET /integrations/triggers
To populate Screen 4/6.
*   **Response:**
```json
[
  {
    "id": "trig_slack_msg",
    "service": "Slack",
    "name": "New Message",
    "description": "Triggers when a message is posted",
    "iconUrl": "https://.../slack.png"
  },
  ...
]
```

#### GET /integrations/actions
To populate Screen 5/7.
*   **Response:**
```json
[
  {
    "id": "act_drive_save",
    "service": "Google Drive",
    "name": "Save File",
    "iconUrl": "https://.../drive.png"
  }
]
```

#### POST /automations
To save the workflow (Screen 8 action).
*   **Request:**
```json
{
  "name": "Save Gmail Attachments",
  "triggerId": "trig_gmail_recv",
  "actionId": "act_drive_save",
  "config": {} // Placeholder for advanced params
}
```
*   **Response (201 Created):**
```json
{
  "id": "auto_999",
  "status": "ACTIVE",
  "createdAt": "2025-10-26T10:00:00Z"
}
```

### 4. History Endpoints

#### GET /automations/history
To populate Screen 9.
*   **Response:**
```json
[
  {
    "id": "exec_555",
    "automationId": "auto_999",
    "automationName": "Reignll.text",
    "status": "SUCCESS", // Enum: SUCCESS, FAILED, PENDING
    "details": "Processed 1 file",
    "executedAt": "2025-10-26T09:45:00Z"
  }
]
```

### 5. Data Models (Kotlin)

```kotlin
@Serializable
data class Workflow(
    val id: String,
    val name: String,
    val trigger: IntegrationNode,
    val action: IntegrationNode,
    val isActive: Boolean
)

enum class ExecutionStatus {
    SUCCESS, FAILED, PENDING
}
```
