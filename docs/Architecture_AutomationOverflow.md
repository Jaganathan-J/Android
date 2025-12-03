# Technical Architecture

### 1. High-Level Overview
The application follows the **MAD (Modern Android Development)** guidelines, utilizing strictly **Clean Architecture** with **MVVM** (Model-View-ViewModel) and **MVI** (Unidirectional Data Flow) patterns. 

### 2. Module Structure
*   `:app` - Integration layer, DI root, Navigation host.
*   `:core:ui` - Design system, shared Composables, Theme.
*   `:core:network` - Retrofit instances, OkHttp interceptors, Network models.
*   `:core:common` - Extension functions, Result wrappers, Dispatcher providers.
*   `:feature:auth` - Login, Signup screens and ViewModels.
*   `:feature:workflow_creation` - The Wizard flow (Screens 3-8).
*   `:feature:dashboard` - History and settings (Screens 9-10).

### 3. Layered Design

#### A. Presentation Layer (UI)
*   **Technology:** Jetpack Compose.
*   **State Management:** `ViewModel` holding a `MutableStateFlow<UiState>`. The UI observes this state using `collectAsStateWithLifecycle()`.
*   **Events:** UI fires events (e.g., `AuthEvent.LoginClicked`) to the ViewModel.

#### B. Domain Layer (Business Logic)
*   **Components:** UseCases (Interactors).
*   **Responsibility:** Pure Kotlin code. No Android dependencies. 
*   **Examples:** `LoginUseCase`, `GetWorkflowsUseCase`, `CreateAutomationUseCase`.

#### C. Data Layer (Repository)
*   **Components:** Repositories (Interfaces in Domain, Implementations in Data).
*   **Sources:** 
    *   *Remote:* Retrofit interfaces (`AuthApi`, `WorkflowApi`).
    *   *Local:* DataStore for session tokens.
*   **Mapper:** Maps DTOs (Data Transfer Objects) to Domain Models.

### 4. Dependency Injection (Hilt)
*   `@HiltAndroidApp` on the Application class.
*   `@AndroidEntryPoint` on the MainActivity.
*   `@Module` / `@InstallIn(SingletonComponent::class)` for providing global instances (Retrofit, DataStore).

### 5. Navigation Strategy
Using **Navigation Compose** with type-safe routes.

```kotlin
// Route Definition
sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object Login : Screen("login")
    data object Dashboard : Screen("dashboard")
    // Wizard is a nested graph
    data object WizardGraph : Screen("wizard_graph")
}
```

### 6. Error Handling
*   **Remote:** `NetworkResult<T>` wrapper class (Success, Error, Loading). 
*   **UI:** `SnackbarHostState` exposed via LocalCompositionProvider to show temporary messages without coupling specific views.
