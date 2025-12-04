# Architecture & Design Patterns: FinanceManager

## 1. Macro Architecture
We adhere to **Google's MAD (Modern Android Development)** guidance, utilizing a strictly typed **Clean Architecture** split into modules to ensure separation of concerns and testing properties.

### 1.1 Module Structure
*   `:app` - DI entry point, Navigation Host.
*   `:core:designsystem` - Color, Type, Theme, Common Composables.
*   `:core:model` - Shared data classes (Workflow, User).
*   `:core:data` - Repositories, Room DB, DataStore.
*   `:core:network` - Retrofit, API Interface.
*   `:feature:auth` - Screens 1 & 2, Auth Logic.
*   `:feature:automation` - Screens 3-8, Builder Logic.
*   `:feature:dashboard` - Screens 9-10, History & Profile.

## 2. Layered Design

### 2.1 UI Layer (Presentation)
*   **Pattern:** MVVM + UDF (Unidirectional Data Flow).
*   **Components:**
    *   `Screen`: Composable function.
    *   `ViewModel`: HiltViewModel. Exposes `uiState` as `StateFlow`.
    *   `UiState`: Sealed Interface (Loading, Success, Error).

### 2.2 Domain Layer
*   **Components:** UseCases (Interactors).
*   **Responsibility:** Pure Kotlin business logic.
*   **Example:** `ValidateWorkflowUseCase` checks if the selected Trigger is compatible with the selected Action.

### 2.3 Data Layer
*   **Pattern:** Repository Pattern.
*   **Responsibility:** Broker between Remote (API) and Local (Cache).
*   **Offline Support:**
    *   Repositories expose `Flow<T>`.
    *   Logic: Emit Local Data (Room) -> Fetch Remote -> Save to Local -> Emit Updated Local.

## 3. Dependency Injection (Hilt)

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit { ... }
}
```

## 4. State Management Strategy
*   Using `Mutation` functions in ViewModels to update `_uiState`.
*   Using `collectAsStateWithLifecycle()` in Composables for lifecycle-safe collection.

## 5. Workflow Engine Logic (The Core)
Since the workflow definition happens on the device (Screens 3-8), the structure is key:
*   **BuilderContext:** A singleton or SharedViewModel logic that holds the `draftWorkflow` object as the user progresses through the linear screens.
*   **Step Validation:** Navigation to the next screen is gated by validation in the Domain layer.