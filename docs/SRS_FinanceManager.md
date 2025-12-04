# Software Requirements Specification (SRS): FinanceManager

## 1. Development Environment
*   **IDE:** Android Studio Ladybug (2024.2.1+)
*   **Language:** Kotlin 2.1.0
*   **Build System:** Gradle 8.10 with Version Catalogs (`libs.versions.toml`)
*   **Min SDK:** API 26 (Android 8.0)
*   **Target SDK:** API 35 (Android 15)

## 2. Technology Stack
### 2.1 UI Layer
*   **Framework:** Jetpack Compose (BOM 2025.01.00)
*   **Navigation:** Jetpack Compose Navigation (Type-safe routes)
*   **Image Loading:** Coil 3.0
*   **Charts:** Vico or MPAndroidChart (Compose wrappers)

### 2.2 Data Layer
*   **Local DB:** Room 2.7 (SQLite with KSP)
*   **Network:** Retrofit 2.11 + OkHttp 4.12
*   **Serialization:** Kotlinx Serialization JSON
*   **Key-Value Store:** DataStore Preferences (Proto)

### 2.3 Architecture & Injection
*   **DI:** Hilt 2.51
*   **Concurrency:** Coroutines + Flow + StateFlow
*   **Pattern:** MVVM (Model-View-ViewModel) with Clean Architecture UseCases

## 3. Non-Functional Requirements
### 3.1 Performance
*   **Cold Start:** < 1.5 seconds to interactive Dashboard.
*   **Frame Rate:** Consistent 60fps (120fps on supported devices) especially during list scrolling in Transactions (SCR-06).
*   **Memory:** Max heap usage < 256MB during heavy chart rendering.

### 3.2 Security
*   **Data at Rest:** All sensitive user data (Access Tokens) MUST be stored in `EncryptedSharedPreferences` or Encrypted DataStore.
*   **Network:** SSL Pinning enabled for API endpoints. `network_security_config.xml` must disallow cleartext traffic.
*   **Biometrics:** App should support BiometricPrompt for unlocking visibility of sensitive balances.

### 3.3 Compliance
*   **Accessibility:** All interactive elements (SCR-05 inputs, Bottom Nav) must have `contentDescription`. Touch targets min 48dp.
*   **Localization:** Support `res/values-es`, `res/values-fr` based on Profile Screen language selection.

## 4. API Reliance
*   App requires a RESTful backend. Mock servers (e.g., Mockoon) will be used for development based on contracts defined in `DataContracts_FinanceManager.md`.