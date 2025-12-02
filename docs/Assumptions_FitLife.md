# Assumptions & Technical Decisions

## 1. UI/UX Assumptions
*   **Typo Correction:** The wireframes contain several typos (e.g., "God job" instead of "Good job", "Timess" in onboarding). The development team assumes these should be corrected in the final string resources (`strings.xml`).
*   **Navigation Handling:** The wireframes show a "Back" arrow on the Home/Profile screen (Screen 3). We assume this is an error or refers to a specific flow context. In the production app, the Profile screen functions as a top-level destination and should likely not have a back arrow unless accessed from a deep link.
*   **Data Completeness:** The wireframe list items (Screen 7) show placeholder data. We assume real exercise data (video URLs, descriptions) will be provided by the backend.

## 2. Technical Decisions
*   **Single Activity Architecture:** We will use a single `MainActivity` hosting a `NavHost`. This reduces memory overhead and standardizes transition animations.
*   **Compose Only:** We will not use XML layouts. This future-proofs the app and increases development velocity.
*   **Coil for Images:** chosen over Glide/Picasso for its native Compose support and Kotlin-first design.
*   **Foreground Service for Timer:** Implicit decision to support "Screen Off" workout tracking. This is critical for battery-saving behavior where users lock their phone during a set.