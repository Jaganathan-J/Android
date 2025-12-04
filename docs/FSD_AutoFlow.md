# Functional Specification Document (FSD): AutoFlow

## 1. User Stories & Acceptance Criteria

### US 1.0: Authentication
**Story:** As a user, I want to log in securely so that my workflows are synced across devices.
**Acceptance Criteria:**
*   User sees Onboarding (Screen 1) with "Log In" option.
*   Login screen (Screen 2) accepts Email/Password.
*   Validation: Email format check (`.+@.+\..+`), Password min 8 chars.
*   On success: Navigate to Home/Dashboard.
*   On failure: Show inline error "Invalid credentials".

### US 2.0: Create Workflow - Naming
**Story:** As a user, I want to name my automation so I can identify it later.
**Acceptance Criteria:**
*   Screen 3 displays input field customized for text.
*   "Continue" button is disabled until at least 3 characters are typed.
*   Input limits: Max 50 characters.

### US 3.0: Configure Trigger & Action
**Story:** As a user, I want to select a service event that starts the automation.
**Acceptance Criteria:**
*   Screen 4/6 displays a predefined list of Triggers (e.g., "Receive Email").
*   Screen 5/7 displays Actions.
*   User must select exactly one item to proceed.
*   Visual selection state (border/checkmark) indicates active choice.

### US 4.0: Workflow Preview & Save
**Story:** As a user, I want to review my configuration before deploying.
**Acceptance Criteria:**
*   Screen 8 renders a summary card chain: Name -> Trigger Icon -> Action Icon.
*   "Save Automation" button commits data to backend API.
*   Show loading state (CircularProgressIndicator) during API call.
*   Navigate to History or Home upon success.

### US 5.0: Execution History
**Story:** As a user, I want to see if my automation ran successfully.
**Acceptance Criteria:**
*   Screen 9 lists historical runs sorted by Date (Desc).
*   Status pills: Green for "Success" (correcting design typo "Sance"), Red for "Failed".
*   Toggle switch allows enabling/disabling the workflow definition globally.

## 2. Feature Flows

### 2.1 Creation Wizard Logic
1.  **State Initialization:** Create a session object `DraftWorkflow`.
2.  **Step 1 (Name):** Update `DraftWorkflow.name`. Validate unique.
3.  **Step 2 (Trigger):** Fetch `GET /services/triggers`. Display list. On Select -> Update `DraftWorkflow.triggerId`.
4.  **Step 3 (Action):** Fetch `GET /services/actions`. Filter compatible actions based on Trigger (optional logic). On Select -> Update `DraftWorkflow.actionId`.
5.  **Step 4 (Save):** `POST /workflows` with JSON payload.

## 3. Validation & Error Handling
*   **Network Errors:** Show Snackbar "No internet connection" with Retry button.
*   **Empty States:** If no History exists (Screen 9), show "No activity yet" illustration.
*   **Input Validation:** Real-time regex validation on Login fields using `Flow` operators (debounce 300ms).