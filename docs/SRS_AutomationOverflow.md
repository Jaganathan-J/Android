# Software Requirements Specification (SRS): Automation Overflow

## 1. Build Environment & Compatibility

### 1.1 SDK Versions (2025 Standards)
*   **Minimum SDK:** API 26 (Android 8.0) - Covers 98% of active devices.
*   **Target SDK:** API 35 (Android 15).
*   **Compile SDK:** API 35.
*   **Language:** Kotlin 2.1.0 with K2 Compiler enabled.

### 1.2 Core Libraries
*   **UI Framework:** Jetpack Compose (BOM 2025.02.00).
*   **Dependency Injection:** Hilt 2.55.
*   **Async:** Kotlin Coroutines 1.10.1 + Flows.
*   **Networking:** Retrofit 2.11 + OkHttp 5.0.0-alpha.14.
*   **Image Loading:** Coil 3.0.
*   **Local Storage:** Room 2.7.0 (KSP).
*   **Navigation:** Navigation Compose 2.9.0 (Type-safe Routes).

## 2. Performance Requirements

### 2.1 Latency
*   **Cold Start:** < 1.5 seconds to interactive state on Pixel 8 equivalent.
*   **List Scrolling:** consistently render at 60fps (or 120Hz on supported displays) in Screen 4, 5, 6, 7, 9.
*   **API Timeouts:** Set strictly to 15 seconds for non-blocking operations.

### 2.2 Resource Usage
*   **Memory:** App should not exceed 150MB heap usage during standard wizard flow.
*   **Battery:** Background sync (History updates) must use `WorkManager` with `Constraint.DEVICE_CHARGING` or generic constrained periodic sync (every 15 mins) to minimize drain.

## 3. Security Requirements

### 3.1 Data in Transit
*   All API traffic must use TLS 1.3.
*   Certificate Pinning enabled for the production API domain.

### 3.2 Data at Rest
*   **Auth Tokens:** Stored strictly in `EncryptedSharedPreferences`.
*   **PII:** User email and name in Profile (Screen 10) must be obfuscated in logs.
*   **Database:** Room database encryption using SQLCipher if sensitive configuration data is cached locally.

## 4. Design & Compliance Constraints

### 4.1 Accessibility (a11y)
*   **TalkBack:** All ImageVectors (Icons) in Screens 4-10 must have meaningful `contentDescription` (e.g., "Select Slack Trigger").
*   **Touch Targets:** Minimum 48dp x 48dp for all list cards and CTAs.
*   **Text Scaling:** UI must support 200% font scaling without layout breakage.

### 4.2 Standards
*   Must adhere to **Material 3 Design Guidelines** (customized for the dark theme observed).
*   Android **Predictive Back** gesture support must be enabled in the Manifest.

## 5. Third-Party Limitations
*   Application depends on 3rd party APIs (Slack, Google) for actual automation execution. Service outages in those providers are outside app scope but must be handled gracefully in the UI (Screen 9 status).