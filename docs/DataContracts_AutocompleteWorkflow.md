# Data Models & API Contracts

## 1. Base URL
`https://api.autocompleteworkflow.com/v1`

## 2. API Endpoints

### 2.1 Authentication
*   **POST** `/auth/login`
    *   Request: `{ "email": "user@example.com", "password": "..." }`
    *   Response: `{ "accessToken": "jwt...", "refreshToken": "...", "user": { ... } }`

### 2.2 Integrations
*   **GET** `/integrations/triggers`
    *   Response: `[ { "id": "gmail_new_email", "name": "New Email", "provider": "Gmail", "iconUrl": "..." }, ... ]`
*   **GET** `/integrations/actions`
    *   Query Params: `?triggerId=gmail_new_email`
    *   Response: `[ { "id": "slack_send_msg", "name": "Send Slack Message", "provider": "Slack" } ]`

### 2.3 Workflows
*   **GET** `/workflows`
    *   Response: `[ { "id": "123", "name": "Daily Report", "enabled": true, "lastRun": "2025-10-26T10:00:00Z", "status": "SUCCESS" } ]`
*   **POST** `/workflows`
    *   Request: `{ "name": "My Flow", "triggerId": "...", "actionId": "...", "config": { ... } }`
*   **PATCH** `/workflows/{id}/status`
    *   Request: `{ "enabled": false }`

## 3. Domain Models (Kotlin)

```kotlin
@Entity(tableName = "workflows")
data class WorkflowEntity(
    @PrimaryKey val id: String,
    val name: String,
    val triggerIcon: String,
    val actionIcon: String,
    val isEnabled: Boolean,
    val lastRunStatus: RunStatus // Enum: SUCCESS, FAILED
)

data class ExecutionLog(
    val id: String,
    val workflowId: String,
    val timestamp: Instant,
    val status: RunStatus,
    val message: String?
)
```

## 4. Error Responses
Standard JSON Problem Details (RFC 7807):
```json
{
  "type": "about:blank",
  "title": "Validation Error",
  "detail": "Workflow name cannot be empty",
  "instance": "/workflows"
}
```