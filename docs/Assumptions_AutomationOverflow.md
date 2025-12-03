# Assumptions & Technical Decisions

### 1. Documentation & Wireframe Gaps
*   **Typos:** The visual analysis highlighted typos in the wireframes (e.g., "Sance", "Uptonked", "Contiate"). This documentation assumes these are placeholder errors and uses corrected, professional English terminology (e.g., "Success", "Uploaded", "Continue").
*   **Missing Sign-Up:** Screen 1 has a "Get Started" button, but no explicit "Sign Up" form design was provided. I assume the "Sign Up" flow mirrors the "Log In" flow (Screen 2) but adds a Confirm Password field.
*   **Integration Configuration:** The wireframes show selecting a trigger (e.g., Slack) but do not show *configuring* it (e.g., which channel?). I assume for MVP this configuration is skipped or happens via a generic web-view based OAuth flow not shown in the designs.

### 2. Backend Architecture
*   **Logic Engine:** The mobile app is a client *interface*. The actual automation logic runs on a cloud backend (AWS/GCP). The app enables/disables these rules via API.
*   **Real-time updates:** The Execution History (Screen 9) relies on Pull-to-Refresh or polling. WebSockets are considered specific to Phase 2 (Should Have) to reduce MVP complexity.

### 3. Technical Decisions
*   **Single Activity Pattern:** We will use a single `MainActivity` with Jetpack Navigation Compose. This is the standard 2025 approach for smoother transitions and shared state management.
*   **Hilt vs Koin:** Hilt is chosen because of its tight integration with Jetpack Compose (`hiltViewModel()`) and Google's official endorsement for enterprise-scale apps.
*   **No XML:** The project will be 100% Kotlin/Compose. No legacy XML layouts will be maintained.

### 4. Third-Party Libraries Strategy
*   **Coil:** chosen over Glide/Picasso for its native Compose support and lighter footprint.
*   **Retrofit:** chosen as the industry standard for REST interactions.
*   **Lottie:** (Optional) Assumed usage for the "Success" state animation layout if static assets are insufficient.
