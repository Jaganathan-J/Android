# Software Requirements Specification (SRS)

## 1. System Environment
- **Target OS**: Android 16 (API 36).
- **Min OS**: Android 10 (API 29).
- **Language**: Kotlin 2.1.0+ (K2 Compiler enabled).

## 2. Technology Stack (Nov 2025 Standard)
- **UI Framework**: Jetpack Compose (BOM 2025.10.00).
- **Navigation**: Type-safe Navigation Compose.
- **DI**: Hilt 2.55.
- **Architecture**: MVI (Model-View-Intent) via Orbit or custom UDF.
- **Async**: Coroutines 1.10.x + StateFlow.
- **Network**: Retrofit 2.12 + OkHttp 5.0 (Alpha).
- **Persistence**: Room 2.7 (KSP).
- **Image Loading**: Coil 3.0.

## 3. Performance Requirements
- **Cold Start**: < 1.5 seconds.
- **Frame Rate**: Consistent 120Hz on supported devices (Pixel 9/10).
- **Memory**: < 250MB average heap usage.

## 4. Security & Compliance
- **Obfuscation**: R8 Full Mode + DexGuard.
- **API Security**: Certificate Pinning (via OkHttp).
- **Secrets Management**: Secrets Gradle Plugin (no API keys in git).
- **GDPR/CCPA**: "Delete Account" feature mandatory in Settings.