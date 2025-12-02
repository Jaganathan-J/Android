# Architecture & Design Patterns: FitLife

## 1. Architectural Principles
We utilize **Clean Architecture** tailored for Android, separating concerns into three distinct layers: **Presentation**, **Domain**, and **Data**. This ensures testability and scalability.

## 2. Module Structure (Gradle)
The project is modularized by feature to optimize build times and enforce separation of concerns.
```text
:app                // DI Root, Navigation Host
:core:ui            // Design System, Theme, Common Composables
:core:model         // Shared Data Classes (Exercise, User)
:core:data          // Repositories, Room DB, API Client
:feature:auth       // Login, Signup
:feature:onboarding // Welcome
:feature:workout    // Lists, Details, Categories
:feature:execution  // Timer, Active Session, Service
:feature:settings   // Settings Screens
```

## 3. Application Layers

### 3.1 Data Layer
*   **Sources:** 
    *   `FitLifeDatabase` (Room): Caches workout definitions.
    *   `FitLifeNetworkDataSource` (Retrofit): Fetches user profile and syncs history.
*   **Repository Pattern:** `WorkoutRepository` exposes `Flow<List<Workout>>`. It handles the "Offline-First" logic (Emit DB data first, refresh from Network).

### 3.2 Domain Layer
*   **Use Cases:** Pure Kotlin classes containing business logic.
    *   `StartWorkoutSessionUseCase`: Initializes the foreground service.
    *   `CalculateCaloriesUseCase`: Computes burn rate based on inputs.

### 3.3 Presentation Layer
*   **Pattern:** MVVM (Model-View-ViewModel).
*   **State Management:** `StateFlow` utilized within ViewModels to expose `UiState` data classes to Composables.
*   **Composables:** Functional UI components that react to state changes.

## 4. Dependency Injection (Hilt)
We use Hilt for constructor injection. 
*   `@Singleton`: Network clients, Database.
*   `@ViewModelScoped`: UseCases.

## 5. Critical Component Design: The Timer
To support the "Active Progress" screen (Screen 8), we cannot rely solely on ViewModel coroutines as they die when the app is backgrounded. 
*   **Solution:** `WorkoutForegroundService`.
*   **Architecture:** The Service holds the "Truth" of the timer. The ViewModel binds to the Service or observes a shared `DataStore`/`Flow` to update the UI.