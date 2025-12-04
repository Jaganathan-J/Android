# Assumptions & Technical Decisions

## 1. Technical Assumptions
*   **Backend Responsibility:** We serve as a client controller. The actual execution of the automation (e.g., listening for the email and sending the Slack message) happens on the backend server, not the Android device. The app configures the rules engine.
*   **Images:** Icons for 3rd party apps (Slack, Gmail) are served via CDN URLs provided by the API, not bundled in the APK (to reduce size and allow dynamic additions).
*   **Connectivity:** The app requires an initial internet connection to fetch the catalog of Triggers/Actions. Caching allows viewing, but creation requires connectivity.

## 2. Library Decisions (Rationale)
*   **Hilt:** Chosen over Koin for compile-time safety and close integration with Jetpack Compose `hiltViewModel()`.
*   **Retrofit:** Industry standard, robust, supports Coroutines natively.
*   **Material 3:** Chosen to future-proof the app layout and utilize dynamic coloring capabilities of Android 12+.
*   **Room:** Provides compile-time SQL verification, crucial for complex query caching.

## 3. Analytics Strategy (Privacy Focused)
*   **Tool:** Firebase Analytics (masked).
*   **Events Tracked:**
    *   `workflow_create_start`
    *   `trigger_selected(provider_name)`
    *   `workflow_save_success`
    *   `error_boundary_triggered`

## 4. Constraint Handling
*   **Unknown Triggers:** If the API returns a Trigger type the app doesn't recognize (e.g., a new integration added to backend but app is outdated), the app will display a "Update Required" placeholder card rather than crashing.