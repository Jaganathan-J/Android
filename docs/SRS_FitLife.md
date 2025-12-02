# Software Requirements Specification (SRS): FitLife

## 1. Solution Overview
FitLife is a native Android application built using the Modern Android Development (MAD) stack. It requires strict adherence to 2025 engineering standards, focusing on performance, modularity, and declarative UI.

## 2. Technical Environment

### 2.1 SDK Requirements
*   **Min SDK:** 26 (Android 8.0 Oreo) - Covers 96% of active devices.
*   **Target SDK:** 35 (Android 15).
*   **Compile SDK:** 35.

### 2.2 Language & Frameworks
*   **Language:** Kotlin 2.1.0 (Strongly typed, Coroutines native).
*   **UI Toolkit:** Jetpack Compose 1.8.0 (Material 3 Design System).
*   **Dependency Injection:** Hilt 2.52.
*   **Asynchronous Processing:** Kotlin Coroutines + Flow.
*   **Architecture:** MVVM (Model-View-ViewModel) with Unidirectional Data Flow (UDF).
*   **Local Storage:** Room 2.7.0 (SQLite) with KSP.
*   **Network:** Retrofit 2.11 + OkHttp 5.0 (Alpha).

## 3. Non-Functional Requirements

### 3.1 Performance
*   **Cold Start:** < 1.5 seconds.
*   **Frame Rate:** Consistent 60fps (120fps on supported displays) during Timer Animation (Screen 8).
*   **Memory:** No memory leaks in `ActiveWorkout` screen (strict cancellation of CoroutineScopes).

### 3.2 Security
*   **Data:** User credentials stored in encrypted `DataStore` or Android Keystore.
*   **Transport:** TLS 1.3 enforced for all API calls.
*   **Obfuscation:** R8 full mode enabled for release builds.

### 3.3 Accessibility (A11y)
*   **Content Description:** All icon buttons (Back Arrow, Chevrons) must have localized descriptions.
*   **Contrast:** Text ratios must meet WCAG AA (verified in UI Analysis: Login Button and Settings Text meet this).
*   **Scale:** Support dynamic font sizing up to 200%.

## 4. Compliance
*   **Android Guidelines:** Follows strictly `core-splashscreen` API and Edge-to-Edge enforcement.
*   **Privacy:** Privacy Policy link accessible from Settings Screen (Ref: Screen 4).