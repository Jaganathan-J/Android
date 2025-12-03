# Functional Specification Document (FSD)
## Project: Automation Overflow

### 1. User Stories & Acceptance Criteria

#### Story 1: Onboarding & Authentication
*   **As a** new user,
*   **I want** to log in using my email and password,
*   **So that** I can access my saved automations securely.

**Acceptance Criteria:**
1.  **Screen 1 (Welcome):** Tapping "Get Started" leads to Sign Up (out of scope for visual analysis but implied), tapping "Log In" leads to Screen 2.
2.  **Screen 2 (Log In):** 
    *   Email field validates format (regex).
    *   Password field masks characters.
    *   "Log In" button is disabled until fields are non-empty.
    *   Successful login navigates to the Home/History screen.
    *   Error showing "Invalid credentials" if API 401s.

#### Story 2: Creating a New Automation (The Wizard)
*   **As a** user,
*   **I want** to set up a new workflow by following a step-by-step guide,
*   **So that** I don't get overwhelmed by configuration options.

**Acceptance Criteria - Step 1: Naming (Screen 3)**
1.  User enters a name (e.g., "Save Gmail Attachments").
2.  Next button ("Continue") is enabled only when input length > 3 chars.

**Acceptance Criteria - Step 2: Trigger Selection (Screen 4/6)**
1.  User sees a list of available triggers (Slack, Drive, etc.).
2.  Tapping a card selects it and auto-advances to the Action step.
3.  Layout supports both List (Variant A/B) based on A/B test flag setup in code.

**Acceptance Criteria - Step 3: Action Selection (Screen 5/7)**
1.  User sees a Grid (Screen 5) or List (Screen 7) of actions.
2.  Tapping an action selects it.

**Acceptance Criteria - Step 4: Preview (Screen 8)**
1.  User sees a summary: Name + Trigger Icon/Title + Action Icon/Title.
2.  Tapping "Save Automation" sends POST request to backend.
3.  Success redirects to Execution History with a Snackbar "Automation Created".

#### Story 3: Monitoring Activity
*   **As a** user,
*   **I want** to see a history of when my automations ran,
*   **So that** I can verify they are working correctly.

**Acceptance Criteria (Screen 9):**
1.  List displays items sorted by date (newest first).
2.  Each row shows: Automation Name, Timestamp, Status Badge.
3.  Status Badge: "Success" (Green), "Failed" (Red), "Pending" (Yellow).
4.  Toggle switch enables/disables the specific automation instance or rule (UX ambiguity resolved: toggle affects the parent rule).

### 2. Validation Logic
*   **Email:** `^[A-Za-z0-9+_.-]+@(.+)$`
*   **Password:** Min 8 chars, at least one number.
*   **Automation Name:** Max 50 chars, no special characters allowed `[^a-zA-Z0-9 ]`.

### 3. Screen Transitions
*   **Standard Forward:** Slide In From Right (Standard Android Material navigation).
*   **Back:** Slide Out To Right.
*   **Modal:** "Choose Trigger" might be treated as a nested navigation graph.

### 4. Edge Cases
*   **No Network:** App should show a cached version of History but disable "Create Automation" flow with a specific UI empty state.
*   **Token Expiry:** If Refresh Token fails, force logout and return to Screen 1.
*   **Long Lists:** Trigger/Action screens must use lazy loading (Pagination) if catalog exceeds 20 items.
