# Data Models & API Contracts

## 1. Base Configuration
*   **Base URL:** `https://api.autoflow-app.com/v1/`
*   **Headers:** 
    *   `Authorization: Bearer <access_token>`
    *   `Content-Type: application/json`
    *   `Accept: application/json`

## 2. API Endpoints

### 2.1 Authentication
**POST** `/auth/login`
*   Request:
    ```json
    { "email": "alex@example.com", "password": "securePass123!" }
    ```
*   Response (200 OK):
    ```json
    {
      "accessToken": "eyJ...",
      "refreshToken": "xyz...",
      "user": { "id": "u1", "name": "Alex", "avatarUrl": "..." }
    }
    ```

### 2.2 Services (Triggers/Actions)
**GET** `/services/definitions?type=trigger`
*   Response (200 OK):
    ```json
    [
      {
        "id": "trig_slack_msg",
        "serviceName": "Slack",
        "serviceIconUrl": "https://cdn.../slack.png",
        "displayName": "New Message",
        "description": "Triggers when a message is posted to a channel"
      }
    ]
    ```

### 2.3 Workflows
**POST** `/workflows` (Create)
*   Request:
    ```json
    {
      "name": "Email to Slack",
      "triggerId": "trig_email_receive",
      "actionId": "act_slack_send",
      "config": {} 
    }
    ```
*   Response (201 Created): `{ "id": "wf_123", "status": "ACTIVE" }`

**GET** `/workflows/history`
*   Response (200 OK):
    ```json
    [
      {
        "executionId": "exec_999",
        "workflowName": "Project Updates",
        "status": "SUCCESS", // Valid mapping for UI "Sance"
        "timestamp": "2025-10-24T14:30:00Z"
      }
    ]
    ```

## 3. Local Database (Room)
**Table: `cached_history`**
| Column | Type | Notes |
| :--- | :--- | :--- |
| id | String | Primary Key |
| workflow_name | String | |
| status | String | Enum(SUCCESS, FAILED) |
| executed_at | Long | Epoch millis |

## 4. Error Handling Contract
All error responses follow RFC 7807:
```json
{
  "type": "about:blank",
  "title": "Validation Error",
  "detail": "Trigger ID is invalid.",
  "instance": "/workflows"
}
```