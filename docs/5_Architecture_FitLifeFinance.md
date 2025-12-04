# Architecture & Design Patterns: FitLife Finance

## 1. High-Level Architecture
The application follows the **Clean Architecture** principles combined with **Modern Android Development (MAD)** guidelines. It uses a multi-module single-activity architecture.

### 1.1 Module Structure
*   `:app` - The application entry point, DI graph assembly, Navigation host.
*   `:core:designsystem` - Material 3 Theme, Color Palette (Green/Red/White), Typography, Common Components (TransactionCard, PrimaryButton).
*   `:core:model` - Shared domain entities (Transaction, User, Budget).
*   `:core:data` - Repository implementations, Room Database, DataStore.
*   `:core:network` - Retrofit services, Interceptors, API Models.
*   `:feature:onboarding` - Screens S00, S01.
*   `:feature:home` - Screen S02.
*   `:feature:analytics` - Screen S03.
*   `:feature:transactions` - Screens S06, S07.
*   `:feature:budget` - Screen S05.
*   `:feature:cards` - Screen S04.
*   `:feature:profile` - Screens S08, S09.

## 2. Layered Design

### 2.1 UI Layer (Presentation)
*   **Pattern:** MVVM (Model-View-ViewModel) + Unidirectional Data Flow (UDF).
*   **State Management:** `ViewModel` exposes `StateFlow<UiState>`. The UI observes this state and emits `Events` back to the ViewModel.
*   **Composition:** All screens are built with **Jetpack Compose**.
    ```kotlin
    // Example State
    data class HomeUiState(
        val isLoading: Boolean = false,
        val user: User? = null,
        val totalBalance: Money = Money.Zero,
        val recentTransactions: List<Transaction> = emptyList()
    )
    ```

### 2.2 Domain Layer
*   **Components:** UseCases (Interactors).
*   **Responsibility:** Encapsulate reusable business logic independent of UI or Data.
*   **Examples:**
    *   `CalculateBudgetUtilizationUseCase`: Input (Category, Transactions) -> Output (Percentage, Status).
    *   `FormatCurrencyUseCase`: Input (BigDecimal, Locale) -> Output (String).

### 2.3 Data Layer
*   **Pattern:** Repository Pattern.
*   **Responsibility:** Arbitrate between Local Data (Room) and Remote Data (API). Implements "Offline-First" strategies.
*   **Components:**
    *   `TransactionRepository`: Exposes `Flow<List<Transaction>>`.
    *   `UserRepository`: Manages Auth tokens and Profile data.

## 3. Dependency Injection (Hilt)
The app uses Hilt for compile-time Dependency Injection.
*   `@Singleton` scope for Retrofit clients and Database.
*   `@ViewModelScoped` for UseCases.
*   Qualifiers (`@IoDispatcher`) for Coroutine Context injection.

## 4. Theming & Design System
Based on the visual analysis:
*   **Primary Color:** `#4CAF50` (Green) - Used for Actions, Income, Active Tabs.
*   **Error/Expense Color:** `#E53935` (Red) - Used for Expenses, Over Budget alerts.
*   **Background:** `#FFFFFF` (White) and `#F5F5F5` (Light Gray).
*   **Typography:** Custom `Typography` object mapping standard Material 3 scales (HeadlineLarge, BodyMedium) to the specific sans-serif font displayed in wireframes.

## 5. Navigation
*   **Library:** `androidx.navigation.compose` with Type-Safe Navigation (Kotlin Serialization).
*   **Implementation:** A central `NavHost` in `MainActivity` handles the bottom bar switching and full-screen modal transitions.