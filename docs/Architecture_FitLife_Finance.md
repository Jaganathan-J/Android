# Architecture & Design Patterns
## Application: FitLife Finance

### 1. High-Level Architecture
We adhere strictly to **Clean Architecture** combined with **MVVM (Model-View-ViewModel)** and **Multi-Module** design to ensure scalability and separation of concerns.

**Layers:**
1.  **UI Data Layer (Presentation):** Composables, ViewModels, State Models.
2.  **Domain Layer:** Use Cases (Interactors), Repository Interfaces, Domain Models. Pure Kotlin, no Android dependencies.
3.  **Data Layer:** Repository Implementations, Data Sources (Room, API), Type Converters.

### 2. Package Structure
```text
com.fitlife.finance
├── app (DI Entry Point)
├── core
│   ├── common       // Result wrappers, Dispatchers
│   ├── designsystem // Color, Type, Theme, Components
│   ├── model        // Shared data models (Transaction, Category)
│   ├── data         // Repository Impls, Room DB
│   └── domain       // UseCases, Repository Interfaces
├── feature
│   ├── auth         // LoginScreen
│   ├── dashboard    // DashboardScreen
│   ├── transaction  // Add/Edit flows
│   └── insights     // Charts and Analysis
```

### 3. Design Patterns

#### 3.1 Unidirectional Data Flow (UDF)
Every screen follows this pattern:
- **State:** `data class DashboardUiState` (Loading, Success(data), Error).
- **Event:** `sealed interface DashboardUiEvent` (OnAddClick, OnRefresh).
- **Effect:** `sealed interface DashboardUiEffect` (NavigateToDetails, ShowSnackbar).

ViewModel exposes `StateFlow<UiState>` collected by the Composable.

#### 3.2 Dependency Injection (Hilt)
- `@HiltViewModel` for ViewModels.
- `SingletonComponent` for Database and Retrofit instances.
- `ViewModelComponent` for specific feature dependencies.

#### 3.3 Repository Pattern
Crucial for abstraction. The ViewModel requests data from `TransactionRepository`. It does not know if data comes from Room or Network.

```kotlin
interface TransactionRepository {
    fun getTransactions(): Flow<List<Transaction>>
    suspend fun addTransaction(t: Transaction)
}
```

### 4. Code Style & Standards
- **Compose:** Use separate files for Screen Content vs. Route logic (Hoisting state).
- **Coroutines:** Always inject `CoroutineDispatcher`. Use `viewModelScope`.
- **Naming:** 
  - Verbs for functions (`getTransactions`).
  - Nouns for classes (`TransactionRepository`).
  - PascallCase for Composables (`DashboardScreen`).