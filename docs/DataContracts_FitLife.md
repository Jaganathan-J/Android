# Data Models & API Contracts

## 1. Core Data Models (Domain)

### 1.1 Exercise
```kotlin
data class Exercise(
    val id: String,
    val name: String,       // e.g., "Squats"
    val categoryId: String, // e.g., "lower_body"
    val durationSec: Int,   // e.g., 60
    val iconUrl: String
)
```

### 1.2 UserProfile
```kotlin
data class UserProfile(
    val id: String,
    val name: String,       // e.g., "Emma Johnson"
    val email: String,
    val avatarUrl: String?
)
```

## 2. API Endpoints (REST / JSON)

### 2.1 Authentication
**POST** `/api/v1/auth/login`
*   **Request:**
    ```json
    {
      "email": "emme@email.com",
      "password": "hashed_secret"
    }
    ```
*   **Response (200 OK):**
    ```json
    {
      "token": "jwt_access_token",
      "user": { "id": "u123", "name": "Emma Johnson" ... }
    }
    ```

### 2.2 Workout Data
**GET** `/api/v1/workouts/categories`
*   **Response:**
    ```json
    [
      {
        "id": "cat_01",
        "name": "Jump Rope",
        "icon_url": "https://assets.fitlife.com/icons/jumprope.png"
      }
      // ... remaining items from Screen 5
    ]
    ```

**GET** `/api/v1/workouts/{id}/details`
*   **Response:** Returns the list structure seen in Screen 7 (Squats, Push Ups, etc.).

### 2.3 Session Sync
**POST** `/api/v1/sessions`
*   **Purpose:** Save the result displayed on Summary Screen (Screen 9).
*   **Request:**
    ```json
    {
      "timestamp": "2025-10-26T15:00:00Z",
      "duration_seconds": 84, // 01:24
      "status": "COMPLETED" // or SKIPPED
    }
    ```

## 3. Local Database (Room Entity)
```kotlin
@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val targetReps: Int,
    val videoUri: String
)
```