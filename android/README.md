# FitLife (Automation Overflow)

This is a production-ready Android Studio project generated for the FitLife workflow automation app.

## Setup & Build
1. Open in Android Studio (Ladybug/Meerkat or newer recommended).
2. Sync Gradle.
3. Run on an emulator or device (Min SDK 26).

## KSP Plugin Resolution
This project uses the classpath fallback method for KSP in the root `build.gradle.kts` to ensure compatibility with Kotlin 2.2.21. If plugin resolution fails, ensure you have internet access to MavenCentral/Google repositories.

## Architecture
- Clean Architecture (Data, Domain, UI)
- Hilt Dependency Injection
- Jetpack Compose (Material 3)
- MVVM + MVI