# Assumptions & Technical Decisions

## 1. Visual Analysis Corrections
The provided wireframes contain placeholder text and typos which are corrected for the engineering scope:
*   *Image:* "Contiate Automatiion" -> *Spec:* "Create Automation".
*   *Image:* "Sance" (Green pill) -> *Spec:* "Success".
*   *Image:* "Trigger Variaton A/B" -> *Spec:* Dynamic list rendering from API.

## 2. Technical Assumptions
*   **Auth Provider:** We assume a custom backend identity provider (IdP). If social login (Google/GitHub) is required later, we will integrate Firebase Auth or AWS Cognito.
*   **Execution Engine:** The mobile app is a *configuration client*. The actual logic (triggering the email server to ping Slack) happens on the backend server. The app does not run background services to poll emails.
*   **Push Notifications:** We assume FCM (Firebase Cloud Messaging) will be used to notify users of workflow failures, though the specific UI for prompts is not in the wireframes.

## 3. Third-Party Libraries Rationale
*   **Hilt:** Standard for DI in Android. Reduces boilerplate compared to manual Dagger.
*   **Coil:** Lightweight image loading, 100% Kotlin Coroutines compatible (unlike Glide).
*   **Retrofit:** Industry standard. No need to reinvent the wheel for REST.
*   **Compose Navigation:** Chosen over 'Voyager' or 'Destinations' to stick to Google's official recommendation for long-term support.

## 4. Design Decisions
*   **Gradient Buttons:** The purple gradients will be implemented using `Brush.horizontalGradient` in a standard Compose `Button` modifier to ensure consistency.
*   **Back Navigation:** We are strictly adhering to the top-left Back Arrow pattern shown in screens, implementing standard stack popping behavior.
*   **Tablet Support:** For MVP, the app is locked to Portrait mode. Tablet UI would require a master-detail (List-Detail) layout refactor not scoped here.