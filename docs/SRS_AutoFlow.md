# Software Requirements Specification (SRS): AutoFlow

## 1. Application Build Environment
To ensure maintainability and strictly follow 2025 Android standards:
*   **Language:** Kotlin 2.1.0 (K2 compiler enabled).
*   **Build System:** Gradle 8.8+ (Kotlin DSL).
*   **JDK:** OpenJDK 21.

## 2. SDK Requirements
*   **minSdk:** 26 (Android 8.0 Oreo) - Covers 98% of active devices while allowing modern APIs.
*   **targetSdk:** 35 (Android 15) - Latest compliance.
*   **compileSdk:** 35.

## 3. Technical Stack

### 3.1 UI Layer
*   **Toolkit:** Jetpack Compose (BOM 2025.02.00).
*   **Navigation:** Jetpack Navigation Compose (Type-safe routes).
*   **Images:** Coil 3.0 (Async loading for service icons).
*   **Typrography/Theme:** Material 3 Design Tokens (Shapes, ColorScheme).

### 3.2 Data & Domain Layer
*   **Asynchronicity:** Kotlin Coroutines & Flow.
*   **Networking:** Retrofit 2.11 + OkHttp 5.0 (Alpha).
*   **Serialization:** Kotlinx Serialization (JSON).
*   **Dependency Injection:** Hilt (Dagger) 2.51.
*   **Local Storage:** DataStore (Proto) for user user session; Room for caching History.

## 4. Performance Requirements
*   **Cold Start:** < 1.5 seconds to first interactive frame.
*   **Frame Rate:** Consistent 60fps (or 90/120Hz where supported). Strict avoidance of recomposition loops in the `LazyColumn` on History screens.
*   **Memory:** Max heap usage < 256MB under normal load.

## 5. Security & Compliance
*   **Network Security:** All traffic must use HTTPS (TLS 1.3). Certificate pinning for Auth endpoints.
*   **Tokens:** OAuth2 Refresh/Access tokens stored in `EncryptedSharedPreferences` (wrapped by DataStore).
*   **Obfuscation:** R8 full mode enabled for release builds.
*   **Data Minimization:** Only send necessary telemetry (Opt-in analytics).