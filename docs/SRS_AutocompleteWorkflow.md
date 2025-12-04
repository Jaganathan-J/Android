# Software Requirements Specification (SRS)

## 1. Build Environment
*   **OS Requirements:** Android 8.0 (Oreo / API 26) to Android 15 (Vanilla Ice Cream / API 35).
*   **Language:** Kotlin 2.1.0+ (Strict mode).
*   **Build System:** Gradle 8.10+ (Kotlin DSL).

## 2. Technical Stack Dependencies

### 2.1 User Interface
*   **Framework:** Jetpack Compose (BOM 2025.02.xx).
*   **Navigation:** Compose Navigation 2.8+ (Type-safe routes).
*   **Images:** Coil 3.0 (Async image loading for icons/avatars).
*   **Design System:** Material 3 (Material You).

### 2.2 Architecture & Injection
*   **DI:** Hilt 2.55+.
*   **Concurrency:** Kotlin Coroutines 1.10.x + Flow.
*   **Architecture Pattern:** Clean Architecture (UI -> ViewModel -> UseCase -> Repository -> DataSource).

### 2.3 Data Layer
*   **Local DB:** Room 2.7+ (SQLite abstraction).
*   **Network:** Retrofit 2.11+ / OkHttp 5.0.
*   **Serialization:** Kotlinx Serialization (JSON).
*   **Storage:** DataStore (Preferences).

### 2.4 Background Processing
*   **Scheduler:** WorkManager 2.10+ (Constraint-based execution for reliable automation syncing).

## 3. Performance Requirements
*   **Cold Start:** < 1.5 seconds on mid-range devices (Pixel 6a equivalent).
*   **Frame Rate:** Consistent 60fps (16ms frame budget) during scrolling lists (Screens 4, 5, 9).
*   **Memory Footprint:** < 150MB average heap usage.
*   **APK Size:** < 25MB (via R8 shrinking and App Bundles).

## 4. Security Requirements
*   **API Security:** All traffic over HTTPS (TLS 1.3). Certificate pinning recommended for Production.
*   **Data at Rest:** All sensitive user data (Access Tokens) stored in `EncryptedSharedPreferences` utilizing Android Keystore System.
*   **Obfuscation:** ProGuard/R8 enabled for release builds.
*   **Screen Privacy:** `FLAG_SECURE` enabled on Login Screen (Screen 2).

## 5. Compliance
*   **GDPR/CCPA:** "Delete Account" functionality must be accessible from Profile (Screen 10).
*   **Accessibility:** Support for TalkBack. Minimum touch target size 48dp. Contrast ratio 4.5:1 for text.