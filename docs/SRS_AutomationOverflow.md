# Software Requirements Specification (SRS)
## Project: Automation Overflow
**Technical Lead:** Senior Architect

### 1. Development Environment
*   **IDE:** Android Studio Meerkat (2024.3.1) or higher.
*   **Figma Link:** [Reference to Mockups Provided]
*   **Version Control:** Git (GitHub Enterprise).

### 2. SDK Requirements (2025 Standards)
*   **Minimum SDK:** API 26 (Android 8.0 Oreo) - Covers ~96% of global devices.
*   **Target SDK:** API 35 (Android 15).
*   **Compile SDK:** API 35.

### 3. Language & Libraries
*   **Language:** Kotlin 2.1.0 with K2 Compiler enabled.
*   **Build System:** Gradle 8.10 with Version Catalogs (`libs.versions.toml`).
*   **UI Framework:** Jetpack Compose (BOM 2025.01.00).
    *   Material 3 Design System.
    *   Accompanist (only for features not yet in stable Compose).
*   **Architecture Components:**
    *   `androidx.lifecycle`: v2.8.x (ViewModel, LifecycleRuntime).
    *   `androidx.navigation:navigation-compose`: v2.8.x (Type-safe navigation).
*   **Dependency Injection:** Hilt (Dagger) v2.55.
*   **Asynchronous Processing:** Kotlin Coroutines & Flow (v1.10.x).
*   **Networking:** Retrofit 2.11 + OkHttp 5.0 (Alpha).
*   **Image Loading:** Coil 3.0 (Compose First).
*   **Local Storage:** DataStore (Preferences/Proto) - *No Room needed for MVP unless caching complex objects*.

### 4. Performance Requirements
*   **App Start Time:** Cold start < 1.5 seconds.
*   **Frame Rate:** Consistent 60fps (or 120Hz on supported displays) during scrolling lists (Screen 9, 6, 7).
*   **Memory Imprint:** < 150MB average heap usage.
*   **APK Size:** < 15MB (using R8 full mode obfuscation and shrinkage).

### 5. Security Requirements
*   **Network Security Config:** Direct traffic to HTTPS only; certificate pinning for backend API.
*   **Data at Rest:** Sensitive tokens (Auth/Refresh) stored in `EncryptedSharedPreferences` (wrapped via DataStore).
*   **Obfuscation:** ProGuard/R8 rules enabled for release builds.
*   **Biometrics:** Optional generic implementation for opening the app (future scope).

### 6. Compliance & Accessibility
*   **WCAG 2.1:** All text elements must maintain a 4.5:1 contrast ratio (Review light grey placeholder text).
*   **Dark Mode:** The design is natively Dark Mode. A Light Mode variant must be programmatically derived or strictly disabled via `AppCompatDelegate`.
*   **Dynamic Type:** UI must scale if user increases system font size (using `sp` units).
