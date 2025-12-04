# Architecture Specification

## 1. High-Level Architecture
We utilize **Clean Architecture** combined with **MVVM (Model-View-ViewModel)** and **UDF (Unidirectional Data Flow)**. The app is modularized by feature to ensure separation of concerns.

### Layers
1.  **UI Layer (Presentation):** Composables + ViewModels. Handles User Events and strictly observes UI State.
2.  **Domain Layer:** Pure Kotlin modules. Contains `UseCases` (Business Logic) and Repository Interfaces. No Android dependencies.
3.  **Data Layer:** Repository Implementations, Data Sources (Retrofit Service, Room DAO).

## 2. Module Structure
```text
:app                // Hilt Setup, NavHost, Theme access
:core:ui            // Design System, Shared Composables (Buttons, Cards)
:core:network       // Retrofit, OkHttp, BaseResponse wrappers
:core:model         // Shared Data classes (Workflow, User)
:feature:auth       // Login, Signup, Onboarding screens
:feature:workflow   // Creation Wizard, History, Preview
:feature:profile    // User settings
```

## 3. Hilt Dependency Injection
*   **`@HiltAndroidApp`**: Application class.
*   **`@Module` / `@InstallIn(SingletonComponent::class)`**: 
    *   `NetworkModule`: Provides Retrofit, OkHttpClient.
    *   `DatabaseModule`: Provides RoomDatabase.
*   **`@ViewModelScoped`**: Feature-specific dependencies (e.g., `WorkflowRepository` binding).

## 4. State Management Strategy
Each screen has a strictly typed `UiState` data class.

```kotlin
// Example for History Screen
sealed interface HistoryUiState {
    data object Loading : HistoryUiState
    data class Success(
        val items: List<HistoryItem>,
        val isConnecting: Boolean = false
    ) : HistoryUiState
    data class Error(val message: String) : HistoryUiState
}
```

The ViewModel exposes a `StateFlow<HistoryUiState>` converted using `stateIn` with `SharingStarted.WhileSubscribed(5000)`.

## 5. Repository Pattern
Repositories act as the single source of truth, mediating between Remote (API) and Local (Cache).

*   `AuthRepository`: `login()`, `logout()`, `refreshSession()`.
*   `WorkflowRepository`: `getTriggers()`, `getActions()`, `createWorkflow()`, `getHistory()`.

## 6. Coroutine Usage
*   **Dispatchers:** Injected via Hilt (do not hardcode `Dispatchers.IO`).
*   **Scope:** `viewModelScope` for UI requests.
*   **Concurrency:** Use `async/await` for parallel fetching of Triggers and Actions if pre-loading.