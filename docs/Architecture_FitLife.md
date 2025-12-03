# Architecture Guidelines & Project Structure

## 1. Architectural Pattern
We follow **Google's Recommended App Architecture (MAD)** using a unidirectional data flow (UDF) managed by StateFlow.

### Layers
1.  **UI Layer:** Composable screens observing `ViewModel.uiState`.
2.  **Domain Layer:** Reusable `UseCase` classes containing pure business logic (no Android dependencies).
3.  **Data Layer:** Repositories acting as specific sources of truth, mediating between Remote (API) and Local (Room/DataStore).

## 2. Package Structure
```text
com.fitlife.automation
├── app                 // Hilt Application, MainActivity, Navigation Graph
├── core
│   ├── designsystem    // Color.kt, Type.kt, Theme.kt, Common Components (Buttons, Cards)
│   ├── model           // Domain models: Workflow, Trigger, Action, User
│   ├── network         // Retrofit Modules, DTOs, ApiServices
│   ├── database        // Room Modules, DAOs, Entities
│   └── datastore       // Session management
├── data
│   ├── repository      // WorkflowRepository, AuthRepository (Impl)
│   └── worker          // WorkManager for offline sync
├── domain
│   ├── usecase         // CreateWorkflowUseCase, GetHistoryUseCase
│   └── repository      // Repository Interfaces
└── feature
    ├── auth            // LoginScreen, LoginViewModel
    ├── dashboard       // HistoryScreen, SettingsScreen
    └── workflow        // InitiateScreen, TriggerSelectScreen, PreviewScreen
```

## 3. Core Components

### 3.1 Network Module (Hilt)
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit { ... }
    
    @Provides
    fun provideFitLifeApi(retrofit: Retrofit): FitLifeApi { ... }
}
```

### 3.2 ViewModel State Pattern
Every Feature ViewModel must expose a single StateFlow.

```kotlin
data class WorkflowCreationUiState(
    val isLoading: Boolean = false,
    val workflowName: String = "",
    val selectedTrigger: Trigger? = null,
    val selectedAction: Action? = null,
    val availableTriggers: List<Trigger> = emptyList(),
    val error: String? = null
)
```

## 4. Theming Strategy (Material 3)
*   **Colors:** 
    *   `primary`: Neon Purple (#7F00FF)
    *   `background`: Midnight Blue (#0A0A1A)
    *   `surface`: Dark Grey/Blue (#14142B)
    *   `onSurface`: White (#FFFFFF)
*   **Shapes:** Custom shapes for Cards (Squircle / RoundedCorner 16.dp).
*   **Code Reference:** `FitLifeTheme` wrapper in `MainActivity`.

## 5. Navigation
Use Type-Safe Navigation (Compose 2.8+).

```kotlin
@Serializable
object LoginRoute

@Serializable
data class TriggerSelectRoute(val workflowName: String)
```