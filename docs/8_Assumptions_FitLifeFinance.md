# Assumptions & Technical Decisions: FitLife Finance

## 1. Technical Assumptions

### 1.1 Backend Availability
*   We assume a fully RESTful backend is provided or will be built in parallel. The endpoints described in Data Contracts are requirements for the backend team.
*   We assume the backend handles the actual bank aggregation (e.g., interfacing with Plaid/Yodlee) and provides normalized data to the mobile app.

### 1.2 Authentication Protocol
*   We assume standard OAuth2 flow with Refresh Tokens. The app will not store passwords locally, only tokens in EncryptedSharedPreferences.
*   Biometric authentication assumes the device has hardware support (Fingerprint/FaceID); fallback to PIN is not scoped for MVP.

### 1.3 Localization
*   For MVP, the app is English-only (`en-US`). Currency symbols are dynamic based on API response, but UI hardstrings are English.

## 2. Third-Party Library Integration

### 2.1 Charting
*   **Decision:** Use **Vico** or **MPAndroidChart** (wrapped in Compose) for the Bar and Donut charts.
*   **Rationale:** Building custom drawn charts in Canvas is time-consuming; Vico is light, Compose-native, and supports the animations mandated in the PRD.

### 2.2 Image Loading
*   **Decision:** **Coil**.
*   **Rationale:** De-facto standard for Compose. Vital for loading Merchant icons efficiently with caching headers to save bandwidth.

### 2.3 Dependency Injection
*   **Decision:** **Hilt**.
*   **Rationale:** While Koin is popular, Hilt provides better compile-time safety and tighter integration with Jetpack libraries (ViewModel, Navigation, WorkManager), which is crucial for a large-scale enterprise finance app.

## 3. Design Implementation
*   **Assumption:** The visual design (Green/White theme) is final. The "Dark Mode" mentioned in the Profile screen implies we must implement a complete `darkColorScheme` in the Compose theme, mapping the white backgrounds to Surface/Background dark grays (`#121212`).

## 4. Analytics Strategy
*   We will integrate **Firebase Analytics** to track screen views and conversion events (e.g., "transaction_added", "budget_created"). 
*   **Crashlytics** will be mandatory for monitoring stability in the financial context.