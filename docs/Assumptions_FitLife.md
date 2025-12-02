# Assumptions & Technical Decisions - FitLife Finance

## 1. Product & UX Assumptions
*   **App Name:** While the prompt specifies "FitLife", the visual content is exclusively finance. We assume "FitLife" refers to "Financial Fitness" and have retained the name while building a FinTech product.
*   **Navigation Model:** The wireframes explicitly show **no bottom navigation bar** on sub-screens (e.g., Transactions, Add Expense). We assume a **Hub-and-Spoke** model where the Dashboard is the single entry point, and all other screens are stacked on top, requiring a Back navigation to return to the hub.
*   **Data Freshness:** We assume data is fetched fresh on app launch but cached locally (Single Source of Truth: Database) to allow offline viewing of the Balance.

## 2. Technical Decisions
*   **Compose Only:** No XML layouts will be used. This simplifies the codebase and reduces APK size.
*   **Single Activity:** We will use one `MainActivity` hosting a `NavHost`. This ensures smooth shared element transitions (if added later) and simplifies memory management.
*   **Gradient Implementation:** Instead of static image assets for the background, we will implement a programmatic `Brush.verticalGradient` in Compose. This handles different screen aspect ratios better than bitmaps.
*   **Currency Handling:** We assume the backend handles multi-currency conversion. The app will display formatted Strings provided by the backend or format local `BigDecimal` using a fixed Locale (US) for the MVP.

## 3. Constraints
*   **Wireframe Fidelity:** The designs provided are wireframes/mid-fidelity. The final implementation will require a designer to provide exact hex codes for the gradients if the approximations (Blue->Purple) are not sufficient.
*   **Security:** Since this is a finance app, we assume SSL Pinning is required for production, though not detailed in wireframes.

## 4. External Service Strategy
*   **Analytics:** Firebase Analytics will be stubbed out.
*   **Crash Reporting:** Firebase Crashlytics integration assumed.