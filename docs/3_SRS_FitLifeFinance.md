# Software Requirements Specification (SRS): FitLife Finance

## 1. Build Environment & SDK Compliance
All development must strictly adhere to the **2025 Android Ecosystem** standards.

### 1.1 Project Configuration
*   **Minimum SDK:** API 26 (Android 8.0 O) - To support 95%+ of active devices.
*   **Target SDK:** API 35 (Android 16) - Latest stable release.
*   **Compile SDK:** API 35.
*   **Language:** Kotlin 2.1.0 (Focus on K2 compiler optimizations).
*   **Build System:** Gradle 8.10 with Kotlin DSL (`build.gradle.kts`).

### 1.2 Key Library Versions
*   **User Interface:** 
    *   `androidx.compose:compose-bom:2025.02.00`
    *   `androidx.compose.material3:material3:1.4.0` (Stable)
    *   `io.coil-kt:coil-compose:3.0.0` (Image Loading)
*   **Architecture & DI:**
    *   `com.google.dagger:hilt-android:2.55`
    *   `androidx.hilt:hilt-navigation-compose:1.2.0`
    *   `androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0`
*   **Asynchronous Operations:**
    *   `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1`
*   **Networking:**
    *   `com.squareup.retrofit2:retrofit:2.11.0`
    *   `com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14`
*   **Local Storage:**
    *   `androidx.room:room-runtime:2.7.0` (KSP support required)
    *   `androidx.datastore:datastore-preferences:1.1.1`

## 2. Performance Requirements
*   **Cold Start Time:** App must render the interactive Home Dashboard within **1.5 seconds** on a Google Pixel 7 reference device.
*   **Frame Rate:** UI must maintain **60fps** consistently, with **120fps** support on capable devices (Pixel 9 Pro, Galaxy S25). Jank stats must remain below 1%.
*   **Memory Usage:** Base memory footprint should not exceed 150MB. Bitmap caching for transaction icons must be strictly managed via Coil.
*   **Network:** Payload compression (GZIP) enabled. API timeouts set to 15s for reads, 10s for writes.

## 3. Security Requirements
*   **Data-at-Rest:** All sensitive user data (JWT tokens, cached account balances) must be encrypted using `EncryptedSharedPreferences` (MasterKey) and SQLCipher for Room databases.
*   **Data-in-Transit:** TLS 1.3 enforcement for all API calls. Certificate Pinning implemented for banking API endpoints.
*   **Authentication:** 
    *   Biometric Prompt (Fingerprint/Face) integration for session resumption.
    *   Screen shielding (Flag_SECURE) enabled on `S04_Accounts` and `S09_Profile` to prevent OS-level screenshots of sensitive data.
*   **Obfuscation:** R8 full mode enabled with detailed ProGuard rules to strip unused metadata.

## 4. Compliance & Regulations
*   **Accessibility (a11y):** 
    *   All interactive elements must have a minimum touch target of **48x48dp**.
    *   Content description (contentDescription) mandatory for all icon-only buttons (Back arrow, Bell icon).
    *   Support for TalkBack traversal order explicitly defined.
*   **Localization:** 
    *   Strings extracted to `strings.xml`.
    *   Currency formatting must respect `Locale.getDefault()` (e.g., 1.234,56 € vs $1,234.56).