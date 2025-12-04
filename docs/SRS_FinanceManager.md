# Software Requirements Specification (SRS): FinanceManager

## 1. Build Environment
*   **Language:** Kotlin 2.1.0 (Strict mode).
*   **Build System:** Gradle 8.10 using Version Catalogs (`libs.versions.toml`).
*   **JDK:** OpenJDK 17 (LTS).

## 2. Android SDK Requirements
*   **Min SDK:** 26 (Android 8.0 Oreo).
*   **Target SDK:** 35 (Android 16) - To align with late 2025 standards.
*   **Compile SDK:** 35.

## 3. Technology Stack

### 3.1 User Interface
*   **Framework:** Jetpack Compose (BOM 2025.10.00).
*   **Navigation:** Jetpack Navigation Compose.
*   **Image Loading:** Coil 3.0 (Async).
*   **Design System:** Material Design 3 (M3) with custom shapes and typography.

### 3.2 Architecture Components
*   **Dependency Injection:** Hilt (Dagger) 2.55.
*   **Concurrency:** Kotlin Coroutines + Flow.
*   **State Management:** ViewModel, SaveableStateHolder, distinctUntilChanged.

### 3.3 Data Layer
*   **Network:** Retrofit 2.11 + OkHttp 5.0 (Alpha).
*   **Serialization:** Kotlinx Serialization (JSON).
*   **Local Database:** Room 2.7 (KMP compatible).
*   **Preferences:** Jetpack DataStore (Proto).

## 4. Non-Functional Requirements

### 4.1 Performance
*   **Cold Start:** < 1.5 seconds.
*   **Frame Rate:** Consistent 60fps (120fps supported) during scrolling on Trigger/Action lists.
*   **Memory:** LeakCanary integrated in debug builds. Zero tolerance for Activity leaks.

### 4.2 Security (Finance Standard)
*   **Certificate Pinning:** Mandatory for all API endpoints.
*   **Obfuscation:** R8 Full Mode enabled for release.
*   **Sensitive Data:** No logging of auth tokens in Logcat. `SecureFlag` enabled on Screen 9 (History) to prevent screenshots of sensitive transaction logs (optional configurable).

### 4.3 Accessibility (a11y)
*   **Compliance:** WCAG 2.1 AA.
*   **Implementation:**
    *   All ImageButtons (Back Arrows) must have `contentDescription`.
    *   Touch targets extended to min 48dp via Compose modifiers.
    *   Custom Colors (Neon/Dark) must pass contrast ratio checks; specifically the Grey text on Dark Blue cards found in **Screen 4** analysis.

## 5. Dependency Management
All dependencies must be declared in `gradle/libs.versions.toml` to ensure consistency across multi-module architecture.