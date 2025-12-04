# Assumptions & Technical Decisions: FinanceManager

## 1. Interpreting the Visuals vs. Description
*   **Conflict:** The user described the app as a "Finance Manager" but provided wireframes for a general-purpose "Workflow Automation" tool (referencing generic Triggers/Actions like Slack and Email).
*   **Resolution:** We are building a **Financial Automation Platform**. The documentation assumes the primary use case is financial (e.g., processing invoices, expense reports) but the technical implementation is that of a flexible Workflow Engine (Trigger -> Action). This satisfies the user's title request while adhering to the rigorous visual evidence.

## 2. Visual Correction Assumption
*   The provided screenshots contain significant lorem ipsum and typographical errors (e.g., "Contiate Automatiion", "Sance").
*   **Decision:** The documentation assumes these are placeholder artifacts. The engineering specs mandate correct English (e.g., "Create Automation", "Success") in the final string resources (`strings.xml`).

## 3. Authentication Strategy
*   **Assumption:** The app uses a custom backend for Auth rather than purely Firebase, given the "Enterprise/Server" nature of the integrations shown (Slack/Gmail).
*   **Decision:** Use OAuth2 tokens (Access/Refresh) managed via `EncryptedSharedPreferences`.

## 4. Third-Party Libraries
*   **Coil:** Chosen for image loading over Glide because it is Kotlin-first and Compose-optimized.
*   **Retrofit:** Industry standard for REST.
*   **Hilt:** Standard for Dependency Injection in modern Android.

## 5. Analytics & Telemetry
*   **Assumption:** We need to track which Integrations are most popular to guide future development.
*   **Decision:** Integrate Firebase Analytics to track events: `select_trigger`, `create_workflow_success`.

## 6. Missing UI Elements
*   **Observation:** No "Sign Up" screen is explicitly detailed in visuals (only Login and Welcome).
*   **Assumption:** The "Sign up" link on Screen 2 leads to a web-view registration or a standard native form similar to Login. We will implement a native Registration screen mirroring the Login layout.