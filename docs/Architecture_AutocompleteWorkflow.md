# Architecture & Engineering Standards

## 1. High-Level Architecture
We adhere strictly to **Clean Architecture** combined with **MVVM** and **Unidirectional Data Flow (UDF)**. The application is modularized by feature to ensure separation of concerns and build speed optimization.

### 1.1 Module Structure
*   `:app` (DI Entry point, Navigation Host)
*   `:core:model` (Shared Domain Entities: Workflow, User)
*   `:core:data` (Repositories, Room DB, Retrofit)
*   `:core:ui` (Theme, Common Composables, Design System)
*   `:feature:auth` (Login, Signup screens)
*   `:feature:workflow` (Creation, History, Listing)
*   `:feature:settings` (Profile)

## 2. Layer Detailed Design

### 2.1 Presentation Layer (UI)
*   **Pattern:** MVVM.
*   **State Management:** `ViewModel` exposes a single `StateFlow<UiState>`.
*   **UI events:** `ViewModel` exposes `SharedFlow<UiEvent>` for one-off events (Navigation, Snackbars).

```kotlin
// Example State
data class WorkflowCreationState(
    val name: String = "",
    val selectedTrigger: Integration? = null,
    val selectedAction: Integration? = null,
    val isLoading: Boolean = false
)
```

### 2.2 Domain Layer
*   **Components:** Use Cases (Interactors).
*   **Responsibility:** Pure Kotlin logic, no Android dependencies.
*   **Example:** `ValidateWorkflowUseCase`, `SyncWorkflowsUseCase`.

### 2.3 Data Layer
*   **Repository Pattern:** The single source of truth. Arbitrates between Local DataSource (Room) and Remote DataSource (Retrofit).
*   **Offline First:** Always display data from Room (Observation). Refresh data from Network in background.

## 3. Dependency Injection (Hilt)
*   `@HiltViewModel` for ViewModels.
*   `@Module` / `@InstallIn(SingletonComponent::class)` for Network and Database.
*   Qualifiers such as `@IoDispatcher` for Coroutine Context injection.

## 4. Background Sync Strategy
Since this is an automation app, state consistency is vital.
*   **WorkManager:** Used for `SyncWorker`.
*   **Trigger:** Runs every 15 minutes or upon Push Notification receipt.
*   **Purpose:** Fetches latest execution history (Screen 9) and syncs local toggle states to server.

## 5. Error Handling
*   **Network:** Interceptors map HTTP codes to Domain Exceptions (`NetworkException`, `AuthException`).
*   **UI:** `Result<T>` wrapper used in ViewModels to handle Loading/Success/Error states elegantly.