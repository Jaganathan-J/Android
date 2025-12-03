# Software Requirements Specification (SRS)

## 1. System Environment
*   **OS Requirements**: Android 8.0 (Oreo) to Android 15.
*   **Device Support**: Phone form factors (Portrait constrained). Tablet support is strictly "letterboxed" for v1.

## 2. Technology Stack (2025 Standard)
*   **Language**: Kotlin 2.1.0 (K2 Compiler enabled).
*   **UI Framework**: Jetpack Compose (BOM 2025.01).
*   **Architecture**: MVVM + Clean Architecture + MVI (Model-View-Intent).
*   **Dependency Injection**: Hilt 2.55.
*   **Asynchronicity**: Kotlin Coroutines + Flow.
*   **Network**: Retrofit 2.11 + OkHttp 5.0 (Alpha).
*   **Local Storage**: Room 2.7 (SQLite) for caching workflows; DataStore for user preferences.
*   **Image Loading**: Coil 3.0.

## 3. Performance Requirements
*   **Startup Time**: Cold start < 1.5 seconds.
*   **Frame Rate**: Consistently 60fps; 120fps on supported devices via RenderNode optimization.
*   **Memory Footprint**: < 200MB avg active usage.
*   **Battery**: Restricted background usage; WorkManager used only for critical syncs.

## 4. Security Requirements
*   **Data in Transit**: TLS 1.3 enforced for all API calls (Certificate Pinning recommended).
*   **Data at Rest**: User tokens stored in `EncryptedSharedPreferences` (Jetpack Security).
*   **Obfuscation**: R8 Full Mode enabled for release builds.
*   **Compliance**: GDPR compliant (User must be able to export/delete data via support request).

## 5. Build Configuration
*   `minSdk`: 26
*   `targetSdk`: 35
*   `compileSdk`: 35
*   `versionCode`: 1
*   `versionName`: "1.0.0"