# Software Requirements Specification (SRS)
## Application: FitLife Finance

### 1. Build Environment & SDK Versions
To ensure maintainability and adherence to 2025 standards, the following build configuration is mandatory.

- **Compile SDK:** 35 (Android 15)
- **Target SDK:** 35
- **Min SDK:** 26 (Android 8.0 Oreo) - Covers ~96% of active devices while allowing modern API usage.
- **Language:** Kotlin 2.1.0 (Strict mode enabled).
- **Build System:** Gradle 8.10 with Version Catalogs (`libs.versions.toml`).

### 2. Technology Stack & Libraries

#### UI Layer (Presentation)
- **Framework:** Jetpack Compose (BOM 2025.02.00 or later).
- **Material Design:** Material 3 (`androidx.compose.material3`).
- **Navigation:** Jetpack Navigation Compose (Type-Safe Navigation with Kotlin Serialization).
- **Image Loading:** Coil 3.0 (AsyncImage for Profile avatars).
- **Charts:** MPAndroidChart (wrapped in Compose) or Vico (Native Compose Charts) - *Decision: Vico for pure Compose compatibility.*

#### Domain Layer (Business Logic)
- **Concurrency:** Kotlin Coroutines & Flow.
- **Dependency Injection:** Hilt (Dagger) 2.55+.

#### Data Layer
- **Local Database:** Room 2.7.0 (SQLite) with KSP processing.
- **Preferences:** DataStore (Proto) - replaces SharedPreferences.
- **Networking (Preparedness):** Retrofit 2.11 + OkHttp 5.0 (Interceptors for logging/auth).
- **Serialization:** Kotlinx Serialization.

### 3. Performance Requirements
- **Cold Start Time:** < 1.5 seconds on mid-range devices (Pixel 6a equivalent).
- **Frame Rate:** Consistent 60fps (16ms frame time) during scrolling of Transaction List.
- **Memory Footprint:** < 200MB average heap usage.
- **Leak Detection:** Integration of LeakCanary in Debug builds.

### 4. Security Requirements
- **Data Storage:** All sensitive financial data in Room must be encrypted using `SQLCipher` if requirements escalate, or at minimum depend on Android's sandbox.
- **API Keys:** Stored in `local.properties` and injected via BuildConfig, never committed to VCS.
- **ProGuard/R8:** Full obsuscation enabled for Release builds.
- **Biometrics:** Use `androidx.biometric` library for login optionality.

### 5. Compliance & Policy
- **Permissions:** Request `POST_NOTIFICATIONS` (Android 13+) only when accessing Settings -> Notifications.
- **Privacy:** No PII sent to analytics servers. Financial data remains local-first.
- **Accessibility:** API requires `contentDescription` on all Icons (charts, buttons). Minimum touch target size 48x48dp per Material guidelines.

### 6. Hardware Constraints
- Application must function in Portrait mode primarily.
- Support for dynamic screen sizing (Foldables) via responsive Compose layouts (using `WindowSizeClass`).