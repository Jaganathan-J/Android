# Assumptions & Technical Decisions

## 1. Product Assumptions
*   **Naming:** The app is named "FitLife" but functions as a Workflow Automation tool. We assume this is a branding choice for "Digital Fitness".
*   **Target Audience:** The dark mode/neon aesthetic targets tech-savvy users/developers.
*   **MVP Scope:** We assume specific API configurations (e.g., Slack API keys) are handled via a web dashboard, or the user is already authenticated with providers on the backend. The mobile app only links them.

## 2. Technical Decisions
*   **Jetpack Compose:** Chosen over XML for 2025 longevity, improved developer velocity, and easy theming.
*   **Hilt:** Chosen over Koin for its compile-time safety and standard integration with Jetpack libraries.
*   **Coroutines/Flow:** Chosen over RxJava to reduce APK size and cognitive load, aligning with Google's "Kotlin First" approach.
*   **Coil:** Chosen over Glide/Picasso for its native Compose integration.

## 3. Third-Party Libraries
*   **Retrofit:** Standard for Networking.
*   **Room:** Standard for Local persistence.
*   **Timber:** For Logging.

## 4. Risks
*   **API Rate Limiting:** Checking workflow status too frequently could hit backend limits. Strategy: Implement exponential backoff in the client.
*   **Battery Drain:** If the app uses background polling, it may drain battery. Strategy: Use `WorkManager` for periodic syncing rather than foreground services.