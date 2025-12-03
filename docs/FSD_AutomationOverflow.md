# Functional Specification Document (FSD)

## 1. Authentication Module
### 1.1 Login Flow
*   **Screen**: `Log In`
*   **Inputs**: Email (Text), Password (Text, Obscured).
*   **Validation**: Email regex check, Password min-length 8 chars.
*   **Action**: Tap "Log In" button.
*   **System logic**: Send POST /auth/login. On 200 OK, store JWT in EncryptedSharedPreferences and navigate to Dashboard.
*   **Error Handling**: Show Snackbar on 401/403 errors.

## 2. Automation Creation Module
### 2.1 Name Definition
*   **Screen**: `Create Automation`
*   **Input**: Automation Name field.
*   **Constraint**: Cannot be empty. Max 50 chars.

### 2.2 Trigger Selection
*   **Screen**: `Choose a Trigger`
*   **UI**: Vertical list of Card components. Each card has an Icon and Title.
*   **Interaction**: Tapping a card selects the trigger ID and pushes the Action Selection screen.

### 2.3 Action Selection
*   **Screen**: `Select an Action`
*   **UI**: Similar to Trigger list. Includes "Save Automation" (or Continue) button.
*   **Logic**: Filter actions based on compatibility with selected trigger (if applicable).

### 2.4 Preview & Commit
*   **Screen**: `Workflow Preview`
*   **Data Display**: Shows Flow Name, Selected Trigger Icon/Title, Selected Action Icon/Title in a timeline view.
*   **Action**: "Save Automation" sends POST /workflows payload.

## 3. History & Monitoring Module
### 3.1 Execution Logs
*   **Screen**: `Execution History`
*   **Data Display**: LazyColumn of execution items.
*   **Status Indicators**: 
    *   Green Toggle/Badge = Success.
    *   Red Toggle/Badge = Failure.
*   **Pagination**: Infinite scroll (load 20 items at a time).

## 4. Profile Module
*   **Screen**: `Profile`
*   **Functions**: Display User Avatar, Name, Email. "Log Out" clears local storage and navigates to Welcome screen.

## 5. Edge Cases & Error Handling
*   **Network Failure**: All Create/Update actions must show a "No Connection" dialog and prevent progression.
*   **Token Expiry**: 401 response on any protected API triggering auto-logout or refresh token flow.
*   **Empty States**: Dashboard and History must show illustration when no data exists.