# Architecture & Style Guide

## 1. High-Level Architecture
We adhere to **Google's MAD (Modern Android Development)** standards using a Clean Architecture approach separating concerns into three layers:

1.  **UI/Presentation Layer**: Composable Screens + ViewModels.
2.  **Domain Layer**: Pure Kotlin UseCases + Repository Interfaces.
3.  **Data Layer**: Repository implementations + Data Sources (Network/DB).

## 2. Module Structure
```text
:app            // DI Assembly, Navigation Host
:core:ui        // Design System, Theme, Common Composables
:core:common    // Extensions, Result<T> wrappers, Dispatchers
:core:network   // Retrofit, OkHttp, Interceptors
:core:database  // Room DAO, Entities
:feature:auth   // Login/Register flows
:feature:home   // Feed, Search
:feature:cart   // Cart, Checkout
:feature:pdp    // Product Details
```

## 3. Design Pattern: MVI with StateFlow
Each ViewModel exposes a single `uiState` (StateFlow) and accepts `uiEvent` (Method calls).

**Example Contract:**
```kotlin
data class PdpUiState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String? = null
)

sealed interface PdpUiEvent {
    data class AddToCart(val sku: String) : PdpUiEvent
    object Refresh : PdpUiEvent
}
```

## 4. Hilt Dependency Injection
- **SingletonComponent**: Network, DB, Analytics.
- **ViewModelComponent**: UseCases, Repositories.
- Uses "Constructor Injection" primarily.