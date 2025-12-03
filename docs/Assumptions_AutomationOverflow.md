# Assumptions & Technical Decisions

## 1. Technical Assumptions
1.  **Backend Execution:** The heavy lifting of connecting APIs (e.g., actually posting to Slack or listening to Gmail) is handled entirely by the backend server. The Android app is purely a **Configuration Controller**.
2.  **Stateless API:** The wizard flow submits data only at the end (Step 8). If the app crashes on Step 5, the draft is lost unless we implement local Draft persistence (which is included in the FSD).
3.  **Push Notifs:** The app relies on Firebase Cloud Messaging (FCM) to update the User on Screen 9 (History) when a background automation succeeds or fails.

## 2. Design Assumptions
1.  **Dark Mode Only:** Based on the 10 screens provided, no Light Mode assets were visible. We assume a forced Dark Mode implementation for v1.0 to reduce development time and match the "Pro tool" aesthetic.
2.  **Linear Flow:** The automation creation is strictly linear (Trigger -> Action). No complex trees (If/Else) are supported in the mobile UI for MVP.

## 3. Library Decisions
1.  **Hilt:** Chosen over Koin for compile-time safety and standard Google recommendation.
2.  **Coil:** Chosen over Glide for native Compose integration and Kotlin-first API.
3.  **Retrofit/OkHttp:** Industry standard, robust pooling and interceptor support.

## 4. Risks & Mitigations
*   **Risk:** API Latency on Screen 4/5 (loading list of 3rd party triggers).
    *   **Mitigation:** Aggressive caching strategy in `Room` and skeleton loading screens (Shimmer effect).
*   **Risk:** Token handling.
    *   **Mitigation:** Automatic Token Refresh using OkHttp Authenticator to prevent users from being logged out unexpectedly.