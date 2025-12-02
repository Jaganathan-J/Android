# Architecture & Engineering Design - FitLife Finance

## 1. Architectural Patterns
We follow **Clean Architecture** combined with **MVVM** (Model-View-ViewModel) and **UDF** (Unidirectional Data Flow).

### 1.1. Layer Separation
*   **:app (UI/Presentation):** Activities, Composables, ViewModels.
*   **:domain (Business Logic):** Use Cases, Repository Interfaces, Entity Models. (Pure Kotlin, no Android dependencies).
*   **:data (Data Access):** Repository Implementations, Room DB, Retrofit Services, DTO Mappers.

## 2. Dependency Injection (Hilt)

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit { ... }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTransactionRepo(impl: TransactionRepositoryImpl): TransactionRepository
}
```

## 3. State Management
Each screen has a single source of truth: `ViewModel` exposing a `StateFlow<UiState>`.

### 3.1. Dashboard State Example
```kotlin
data class DashboardUiState(
    val isLoading: Boolean = false,
    val userProfile: UserProfile? = null,
    val totalBalance: Money = Money.zero(),
    val recentSpending: Money = Money.zero(),
    val cards: List<PaymentCard> = emptyList(),
    val error: String? = null
)
```

### 3.2. ViewModel Implementation
```kotlin
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardSummaryUseCase: GetDashboardSummaryUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = getDashboardSummaryUseCase()
            // Handle Result.Success / Result.Error
        }
    }
}
```

## 4. Folder Structure
```
com.fitlife.finance
├── app
│   ├── di
│   ├── ui
│   │   ├── theme
│   │   ├── components (Shared UI: Cards, Buttons)
│   │   ├── screens
│   │       ├── dashboard
│   │       ├── transactions
│   │       ├── budget
│   ├── navigation (NavGraph)
├── domain
│   ├── model
│   ├── repository
│   ├── usecase
├── data
│   ├── local (Room DAO)
│   ├── remote (Retrofit API)
│   ├── repository
```