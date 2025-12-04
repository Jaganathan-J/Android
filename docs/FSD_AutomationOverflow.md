### Functional Specification Document (FSD)

#### User Stories
1. **Onboarding Flow:**
   - As a new user, I want to log in or sign up seamlessly.
   - Acceptance Criteria: Secure authentication with email/password or OAuth.
2. **Workflow Creation:**
   - As a user, I need to create and name an automation workflow.
   - Acceptance Criteria: Form validation for non-empty names.
3. **Trigger Selection:**
   - Users should select from available triggers (e.g., email receipt).
   - Acceptance Criteria: List view with tappable cards.
4. **Action Selection:**
   - After choosing a trigger, users select an action (e.g., Slack message).
   - Acceptance Criteria: List view with save button at the bottom.
5. **Execution History:**
   - Users need to review past workflow runs.
   - Acceptance Criteria: Filterable list with status indicators.
6. **Profile Management:**
   - Users can edit their profile and log out.
   - Acceptance Criteria: Secure logout functionality.

#### Feature Flows
1. Onboarding:
   - Splash → Login/Signup → Automation Creation.
2. Workflow Creation:
   - Name input → Trigger selection → Action selection → Preview → Save.
3. Execution History:
   - Main screen → History view with status details.
4. Profile Management:
   - Settings menu → Profile edit → Logout.

#### Edge Cases
- Empty field validation on form submissions.
- Handling invalid triggers/actions during workflow creation.
- Network errors during API calls.

#### Screen Transitions
1. Onboarding: Splash → Login → Create Automation.
2. Workflow Creation: Trigger selection → Action selection → Preview → Save.
3. Profile Management: Settings → Logout → Login.
4. Execution History: Main dashboard → History view.

#### Validation Logic and Error Handling
- Input validation with toast messages for invalid entries.
- API errors displayed as snackbar notifications.
- Loading states during network requests.