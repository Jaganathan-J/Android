### Architecture Document

#### Layered Architecture
1. **UI Layer**: Compose Multiplatform for cross-platform compatibility.
2. **Domain Layer**: Clean architecture with use cases and repositories.
3. **Data Layer**: Room Database as local DB, REST API for remote data.
4. **DI Layer**: Hilt for dependency injection across components.

#### Key Components
1. **Task Repository**:
   - Manages CRUD operations for tasks.
   - Handles sync with cloud storage using WorkManager.
2. **Notification Service**:
   - Schedules and cancels notifications based on task status.
   - Integrates with Firebase Cloud Messaging for push notifications.
3. **Collaboration Module**:
   - Manages sharing state and real-time updates.
   - Uses WebSocket for live collaboration features.
4. **Authentication Provider**:
   - Handles sign-up/login via Google/Apple APIs.
   - Implements role-based access control (RBAC).

#### Dependency Management
1. **UI**: Jetpack Compose, Material Design icons.
2. **Networking**: Retrofit for API calls, OkHttp as default client.
3. **Database**: Room with Kotlin Coroutines support.
4. **DI**: Hilt for scoped dependencies and module injection.
5. **State Management**: Jetpack Navigation, ViewModelScope for task management.