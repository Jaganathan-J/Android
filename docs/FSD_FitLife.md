# Functional Specification Document (FSD): FitLife Automation

**System:** Android Mobile Application  
**Format:** Native (Jetpack Compose)

## 1. Authentication Module

### 1.1 Login Flow
*   **User Story:** As a user, I want to log in so that I can access my saved workflows.
*   **Screen Reference:** Screen 2 (Log In).
*   **Input:** Email (String, Valid Email Format), Password (String, masked).
*   **Action:** Tap "Log In" (Primary Gradient Button).
*   **Validation:**
    *   Email must match regex `^[A-Za-z0-9+_.-]+@(.+)$`.
    *   Password must be > 6 characters.
*   **Error States:** Show Snackbar "Invalid credentials" on 401 response.

### 1.2 Splash & Onboarding
*   **User Story:** As a new user, I want to understand the app's value.
*   **Screen Reference:** Screen 1.
*   **Behavior:** Static screen displaying Logo and "Get Started" button. Checks `DataStore` for existing Auth Token. Auto-navigates to Dashboard if valid.

## 2. Workflow Creation Module

### 2.1 Initiate Automation
*   **User Story:** As a user, I want to name my new task.
*   **Screen Reference:** Screen 3.
*   **Behavior:** Form input for "Workflow Name".
*   **Constraints:** Name cannot be empty. "Continue" button disabled until length > 3.
*   **Correction:** Fix typo "Contiate Automatiion" to "Initiate Automation" in UI implementation.

### 2.2 Select Trigger
*   **User Story:** As a user, I want to choose what starts the automation.
*   **Screen Reference:** Screen 4 (V1) & Screen 6 (V2).
*   **Component:** `LazyColumn` containing `SelectionCard` items.
*   **Data:** List of available integrations (e.g., "New Email", "Slack Message", "Calendar Event").
*   **Interaction:** Tapping a card highlights it momentarily and navigates to Action Selection.

### 2.3 Select Action
*   **User Story:** As a user, I want to choose what happens when the trigger fires.
*   **Screen Reference:** Screen 5 (V1) & Screen 7 (V2).
*   **Component:** `LazyColumn` of `SelectionCard`.
*   **Data:** List of output capabilities (e.g., "Send Email", "Create Jira Ticket").
*   **Interaction:** Tapping a card selects it. User must tap "Continue" (Bottom Docked) or auto-advance (configurable).

### 2.4 Preview & Save
*   **User Story:** As a user, I want to verify the connection before enabling it.
*   **Screen Reference:** Screen 8.
*   **Display:** Visual connector showing [Trigger Icon] -> [Action Icon].
*   **Action:** Tap "Save Automation".
*   **Backend Logic:** POST payload to `/api/v1/workflows` with trigger_id, action_id, and name.

## 3. Dashboard & History

### 3.1 Execution History
*   **User Story:** As a user, I want to see if my automations worked.
*   **Screen Reference:** Screen 9.
*   **Display:** List of past runs. Each item shows Workflow Name, Date, and Status Badge (Success/Failed).
*   **Toggle:** Main toggle to enable/disable the workflow globally.
*   **Colors:** Green badge for "Success", Red badge for "Failed".

## 4. Settings

### 4.1 Profile
*   **Screen Reference:** Screen 10.
*   **Content:** User Avatar, Name, Email (Read-only from Auth profile).
*   **Actions:** "Log Out" clears tokens and navigates to Login.

## 5. Edge Cases & Error Handling
*   **Network Failure:** If offline, "Save Automation" should queue the request in Room DB (WorkManager) and sync when online.
*   **Empty State:** If History is empty, show a "No executions yet" illustration.
*   **API Errors:** Global error handling via Retrofit `Interceptor` effectively mapping HTTP codes to user-friendly Snackbars.