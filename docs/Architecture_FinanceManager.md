# Architecture & Design Patterns

## 1. High-Level Architecture
We adhere to **Google's MAD (Modern Android Development)** recommendations using **Clean Architecture** organized by feature modules.

### Layers
1.  **UI Layer:** Jetpack Compose `Screen` composables + `ViewModel`.
2.  **Domain Layer:** Pure Kotlin `UseCase` classes + `Repository` interfaces.
3.  **Data Layer:** Repository implementations, Data Sources (Remote/Local), DTO mappers.

## 2. Module Structure
```text
:app
:core:model         // Shared domain models (Transaction, Account)
:core:data          // Repositories & DataSources
:core:network       // Retrofit & API definitions
:core:database      // Room entities & DAOs
:core:ui            // Shared Composables (FinanceCard, AppButton)
:feature:auth       // Sign In, Onboarding screens
:feature:dashboard  // Home screen logic
:feature:budget     // Budget planner logic
:feature:analytics  // Charts & Analysis
:feature:transactions // List & Add Expense
```

## 3. Dependency Injection (Hilt)

### Repository Module
```kotlin
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindTransactionRepository(impl: OfflineFirstTransactionRepository): TransactionRepository
    
    @Binds
    fun bindAccountRepository(impl: RealAccountRepository): AccountRepository
}
```

## 4. Key Patterns

### 4.1 State Management (UDF)
Every screen implements a Unidirectional Data Flow.

*   **State:** `data class BudgetUiState(val budgets: List<Budget>, val isLoading: Boolean)`
*   **Events:** `sealed interface BudgetEvent { data class ChangeMonth(val month: YearMonth): BudgetEvent }`
*   **ViewModel:** Exposes `StateFlow<BudgetUiState>`.

### 4.2 Offline-First
Data is strictly read from the **Room Database** (Single Source of Truth). The Repository fetches from Network, updates the DB, and the UI observes the DB via Flow.

### 4.3 Error Handling
Return types from UseCases utilize a generic `Result<T>` wrapper to encapsulate `Success`, `Error`, or `Loading` states, propagating exceptions from the Data Layer to the UI for Snackbar display.

## 5. Navigation Architecture
Using **Compose Navigation** with type-safe arguments.

```kotlin
@Serializable
object HomeRoute

@Serializable
data class DetailsRoute(val transactionId: String)
```