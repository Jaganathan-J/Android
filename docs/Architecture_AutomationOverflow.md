# Architecture & Engineering Standards

## 1. High-Level Architecture
The app follows **Clean Architecture** with strict separation of concerns, divided into three layers per feature module.

### Layers
1.  **UI Layer (Presentation)**: Jetpack Compose, ViewModels, UI State (StateFlow).
2.  **Domain Layer**: Pure Kotlin, Use Cases, Repository Interfaces. No Android dependencies.
3.  **Data Layer**: Repositories (Impl), Data Sources (Retrofit/Room), Mappers.

## 2. Module Structure
```
:app                // DI assembly, NavHost
:core
  :model            // Shared data classes
  :network          // Retrofit, OkHttp, Interceptors
  :datastore        // Proto DataStore for prefs
  :designsystem     // Theme, Typography, Common Composables
:feature
  :auth             // Login screens & logic
  :automation       // Wizard flow & dashboard
  :history          // Execution logs
  :profile          // Settings
```

## 3. Design Patterns

### UI Layer: Uni-Directional Data Flow (UDF)
```kotlin
// Example ViewModel Contract
data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false
)

sealed interface LoginIntent {
    data class Submit(val e: String, val p: String) : LoginIntent
}
```

### Domain Layer: Use Cases
Individual Use Cases prevent ViewModel bloat.
*   `GetTriggersUseCase`
*   `PerformLoginUseCase`
*   `SyncHistoryUseCase`

### Data Layer: Repository Pattern
Repositories arbitrate between Remote (API) and Local (Room) data source to provide a "Single Source of Truth" (SSOT) to the Domain layer.

## 4. Dependency Injection (Hilt)
*   `@HiltAndroidApp` on UserApplication.
*   `@AndroidEntryPoint` on Activities.
*   `@Module` / `@InstallIn(SingletonComponent::class)` for NetworkModule (Retrofit providers).

## 5. Navigation
Type-safe navigation using **Jetpack Navigation Compose**.
```kotlin
// Deep link capable routes
@Serializable object LoginRoute
@Serializable object DashboardRoute
@Serializable data class HistoryRoute(val workflowId: String?)
```