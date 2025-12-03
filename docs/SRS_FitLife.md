# Software Requirements Specification (SRS)

## 1. Build Environment
*   **OS:** macOS / Linux / Windows
*   **IDE:** Android Studio Meerkat (2024.3.1) or later
*   **Build System:** Gradle 8.10+
*   **Language:** Kotlin 2.1.0

## 2. SDK Requirements
*   **minSdk:** 26 (Android 8.0 Oreo) - Covers ~96% of devices.
*   **targetSdk:** 35 (Android 16)
*   **compileSdk:** 35

## 3. Technology Stack

### 3.1 UI Layer (Presentation)
*   **Framework:** Jetpack Compose (BOM 2025.02.00)
*   **Navigation:** Jetpack Navigation Compose (Type-safe, v2.8.0+)
*   **Image Loading:** Coil 3.1 (Async image loading for icons/avatars)
*   **Architecture Pattern:** MVVM (Model-View-ViewModel) + MVI (Unidirectional Data Flow)

### 3.2 Domain Layer (Business Logic)
*   **Pattern:** Clean Architecture UseCases
*   **Concurrency:** Kotlin Coroutines & Flow

### 3.3 Data Layer
*   **Network:** Retrofit 2.11 + OkHttp 4.12
*   **Serialization:** Kotlinx Serialization (JSON)
*   **Local Storage:** Room 2.7 (SQLite abstraction) for caching workflows/history.
*   **Preferences:** Jetpack DataStore (Proto) for user session.

### 3.4 Dependency Injection
*   **Framework:** Dagger Hilt 2.55

## 4. UI/UX Requirements
*   **Theme:** Material 3 (Material You). Implementation must override default colors to match "Deep Midnight Blue" (#0A0A1A) and "Neon Purple" (#7F00FF).
*   **Typography:** Google Fonts (Inter or Roboto), accessible scaling.
*   **Dark Mode:** Default is Dark. Light mode is optional/secondary.

## 5. Performance Requirements
*   **Cold Start:** < 1.5 seconds.
*   **Frame Rate:** Consistent 60fps (120fps supported) during scrolling of History lists.
*   **Memory Usage:** < 200MB avg.

## 6. Security Requirements
*   **Transport:** All network traffic must use HTTPS (TLS 1.3).
*   **Storage:** Auth tokens must be encrypted using `EncryptedSharedPreferences` or DataStore.
*   **Obfuscation:** Release builds must use R8 full mode.

## 7. Compliance
*   **Permissions:** Request `POST_NOTIFICATIONS` at runtime on Android 13+.
*   **GDPR:** Provide "Delete Account" option in settings (link to web handling).