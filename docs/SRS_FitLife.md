# Software Requirements Specification (SRS) - FitLife Finance

**Version:** 1.0.0
**Target Platform:** Android Mobile

## 1. Build Environment & SDKs
All development must strictly adhere to 2025 Modern Android Development (MAD) standards.

*   **IDE:** Android Studio Koala Feature Drop 2024.2.1 (or latest stable).
*   **Language:** Kotlin 2.0.20 (K2 Compiler enabled).
*   **Grade Plugin:** AGP 8.6.0.
*   **Min SDK:** API 26 (Android 8.0 Oreo) - 98% device coverage.
*   **Target SDK:** API 35 (Android 15).
*   **Compile SDK:** API 35.

## 2. Libraries & Dependencies

### 2.1. UI Layer
*   `androidx.compose:compose-bom:2025.02.00`
*   `androidx.compose.material3:material3:1.3.1` (Material Design 3)
*   `androidx.navigation:navigation-compose:2.8.0` (Type-safe navigation)
*   `io.coil-kt:coil-compose:2.7.0` (Image loading)
*   `com.google.accompanist` (Deprecated features verified migrated to Foundation).

### 2.2. Architecture & DI
*   `com.google.dagger:hilt-android:2.51.1`
*   `androidx.hilt:hilt-navigation-compose:1.2.0`
*   `androidx.lifecycle:lifecycle-runtime-compose:2.8.4`

### 2.3. Data & Networking
*   `com.squareup.retrofit2:retrofit:2.11.0`
*   `com.squareup.okhttp3:logging-interceptor:4.12.0`
*   `androidx.room:room-runtime:2.6.1` (KSP enabled)
*   `androidx.datastore:datastore-preferences:1.1.1`
*   `org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1`

## 3. Non-Functional Requirements

### 3.1. Performance
*   **Frame Rendering:** Manage 99% of frames under 16ms (60fps) using Compose Layout Inspector validation.
*   **Startup Time:** Cold start < 1.5s.
*   **Memory:** Baseline profile usage < 120MB.

### 3.2. Security
*   **Data Store:** User tokens stored in `EncryptedSharedPreferences` (MasterKey).
*   **Transport:** TLS 1.3 enforced via Network Security Config.
*   **Obfuscation:** R8 Full Mode enabled in Release builds.

### 3.3. Compliance
*   **Accessibility:** All Interactive elements must have `minTouchTargetSize = 48.dp`.
*   **Dark Mode:** Force Dark Mode compliance as the UI is natively dark (Gradient background). Support a Light Mode inversion mapping if required, but default to the wireframe's Dark aesthetic.

### 3.4. Localization
*   Base language: English (US).
*   Currency formatting linked to `Locale.getDefault()` logic, but defaulting to USD ($) based on wireframes.