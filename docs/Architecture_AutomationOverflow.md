# Architecture & Engineering Design: Automation Overflow

## 1. Architectural Pattern
We follow **Google's Official Guide to App Architecture (MAD)** using **Clean Architecture** principles.

### Layers
1.  **UI Layer (Presentation):** Jetpack Compose, ViewModels, UI State (StateFlow).
2.  **Domain Layer:** UseCases (Interactors), Domain Models, Repository Interfaces. Pure Kotlin, no Android dependencies.
3.  **Data Layer:** Repositories (Impl), Data Sources (Remote/Local), API Services (Retrofit), DB (Room).

## 2. Package Structure

```text
com.automationoverflow.app
├── core
│   ├── designsystem    // Theme, Color, Type, Components
│   ├── network         // Retrofit, OkHttp, Interceptors
│   ├── database        // Room DAO, Entitites
│   └── model           // Shared domain models (Trigger, Action)
├── feature
│   ├── auth            // Login, Signup Logic
│   ├── workflow_create // The Wizard Flow (Screens 3-8)
│   ├── history         // Execution Logs (Screen 9)
│   └── profile         // Settings (Screen 10)
├── domain
│   ├── repository      // Interfaces
│   └── usecase         // GetTriggersUseCase, CreateAutomationUseCase
└── app                 // DI Roots, NavGraph, MainActivity
```

## 3. Dependency Injection (Hilt)

### Modules
*   `NetworkModule`: Provides `Retrofit`, `OkHttpClient`.
*   `DatabaseModule`: Provides `AppDatabase`, `HistoryDao`.
*   `RepositoryModule`: Binds `AuthRepositoryImpl` to `AuthRepository`, etc.

## 4. State Management
*   **Unidirectional Data Flow (UDF):**
    *   **Events:** UI sends `Sealed Class` events (e.g., `CreateWorkflowEvent.OnTriggerSelected(id)`) to ViewModel.
    *   **State:** ViewModel exposes `StateFlow<CreateWorkflowUiState>`.
    *   **UI:** Composable observes StateFlow via `collectAsStateWithLifecycle()`.

## 5. Domain Logic Details

### Repository Pattern
All data access is mediated through Repositories. 
*   `WorkflowRepository` handles fetching available Triggers/Actions (cached locally for speed) and posting final configurations.

### Use Cases
*   `ValidateLoginUseCase`: Encapsulates regex logic for email.
*   `SubmitAutomationUseCase`: Combines TriggerID + ActionID + Name, maps to DTO, and handles network execution.

## 6. Theming Approach
Using `MaterialTheme` wrapper:
```kotlin
object AppColors {
    val Background = Color(0xFF0A0A14)
    val Surface = Color(0xFF141423)
    val GradientPrimary = Brush.horizontalGradient(listOf(Color(0xFF7F00FF), Color(0xFF00D4FF)))
}
```
This ensures the specific dark mode aesthetic (Screen 1-10) is applied globally.