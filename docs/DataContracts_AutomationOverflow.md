# Data Models & API Contracts: Automation Overflow

## 1. API Standards
*   **Base URL:** `https://api.automationoverflow.com/v1/`
*   **Auth:** Bearer Token via `Authorization` header.
*   **Format:** JSON (proto3 optional for future).

## 2. API Endpoints

### 2.1 Authentication
**POST** `/auth/login`
*   **Request:**
    ```json
    { "email": "alex@example.com", "password": "s3cr3t" }
    ```
*   **Response (200 OK):**
    ```json
    { "token": "eyJhbGciOi...", "user": { "id": "u123", "name": "Alex", "avatarUrl": "..." } }
    ```

### 2.2 Configuration Metadata
**GET** `/config/triggers`
*   **Response:**
    ```json
    [
      { "id": "t_gmail_rec", "name": "Receive Email", "icon_url": "...", "provider": "gmail" },
      { "id": "t_form_sub", "name": "Form Submission", "icon_url": "...", "provider": "typeform" }
    ]
    ```

**GET** `/config/actions`
*   **Response:**
    ```json
    [
      { "id": "a_slack_msg", "name": "Send Slack Message", "provider": "slack" },
      { "id": "a_cal_evt", "name": "Create Calendar Event", "provider": "gcal" }
    ]
    ```

### 2.3 Workflows
**POST** `/workflows`
*   **Purpose:** Corresponds to "Save Automation" on Screen 8.
*   **Request:**
    ```json
    {
      "name": "Daily Summary Email",
      "trigger_id": "t_gmail_rec",
      "action_id": "a_slack_msg",
      "is_active": true
    }
    ```
*   **Response (201 Created):**
    ```json
    { "id": "w_998877", "status": "active", "created_at": "2025-10-26T10:00:00Z" }
    ```

**GET** `/workflows/history`
*   **Purpose:** Populates Screen 9.
*   **Response:**
    ```json
    {
      "items": [
        {
          "workflow_id": "w_998877",
          "workflow_name": "Daily Summary",
          "status": "success", // or "failed", "running"
          "timestamp": "2025-10-26T09:00:00Z"
        }
      ]
    }
    ```

## 3. Local Database (Room)

### Entity: `WorkflowEntity`
```kotlin
@Entity(tableName = "workflows")
data class WorkflowEntity(
    @PrimaryKey val id: String,
    val name: String,
    val triggerId: String,
    val actionId: String,
    val lastRunStatus: String
)
```

### Entity: `HistoryEntity`
Used for offline caching of Screen 9 data.

## 4. Error Codes
*   `401`: Unauthorized (Logout user).
*   `422`: Validation Error (e.g., Trigger not compatible with Action).
*   `503`: Service Unavailable (Dependent API down).