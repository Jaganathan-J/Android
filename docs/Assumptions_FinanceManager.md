# Assumptions & Technical Decisions

## 1. Technical Assumptions
*   **Backend Availability:** We assume a fully functional backend exists that implements the APIs defined in `DataContracts`. The initial version will use a Mock server response interceptor.
*   **Currency:** The initial release assumes Single Currency (USD) support. Multi-currency architecture is planned but out of scope for MVP.
*   **Bank Integration:** We assume the existence of a third-party aggregator (like Plaid or Yodlee). The "Link Account" flow in the FSD assumes we are handing off to a web view or SDK provided by this vendor, receiving a token back.

## 2. Design Assumptions
*   **Recurring Transactions:** We assume the backend handles the logic of generating the repeated transaction records. The mobile app only sends the configuration (frequency, start date).
*   **Notifications:** We assume Firebase Cloud Messaging (FCM) is setup. The UI for Notifications (SCR-09) displays a local persisted history of these push payloads.

## 3 Third-Party Libraries Rationale
*   **Retrofit/OkHttp:** Industry standard, robust for networking.
*   **Hilt:** Google's recommended DI solution, reduces boilerplate compared to Dagger 2.
*   **Coil:** Lightweight image loading specifically designed for Compose (unlike Glide which is View-based legacy).
*   **Vico Charts:** Chosen over MPAndroidChart because Vico is built natively for Jetpack Compose, ensuring better performance and state handling in the Analytics screen.

## 4. Security Decisions
*   **Biometrics:** We assume the device has secure hardware. We will use `BiometricManager.Authenticators.BIOMETRIC_STRONG`.
*   **Session Management:** Access tokens are short-lived (1 hour). Refresh tokens are long-lived and stored securely encryped.