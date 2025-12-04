# Functional Specification Document (FSD)

## 1. Introduction
This document details the functional behavior of the Autocomplete Workflow Android application, mapping the visual wireframes to specific logic and acceptance criteria.

## 2. Authentication Module

### 2.1 User Login
*   **UI Reference:** Screen 2 (Log In).
*   **Inputs:** Email (Text, Email InputType), Password (Text, Password InputType).
*   **Logic:**
    *   Validate Email format utilizing Android's `Patterns.EMAIL_ADDRESS`.
    *   Validate Password > 8 characters.
    *   Disable "Log In" button (State: Disabled) until validation passes.
    *   On Success: Transition to Dashboard.
    *   On Failure: Show Snackbar error message.

## 3. Workflow Creation Wizard (The Core Loop)

### 3.1 Step 1: Naming
*   **UI Reference:** Screen 3 (Create Automation).
*   **Logic:**
    *   User enters a unique name.
    *   Local validation: Check for duplicate names in local Room DB cache.
    *   Action: Tap "Continue" -> navigate to Trigger Selection.

### 3.2 Step 2: Trigger Selection
*   **UI Reference:** Screen 4/6 (Choose a Trigger).
*   **Data Source:** `GET /api/v1/integrations/triggers`.
*   **Interaction:** Single-select list. Creating a "Selected" state highlighting the card.
*   **Logic:** Fetch list asynchronously. Show Shimmer loading state. Error state allows retry.

### 3.3 Step 3: Action Selection
*   **UI Reference:** Screen 5/7 (Select an Action).
*   **Data Source:** `GET /api/v1/integrations/actions?compatible_with={trigger_id}`.
*   **Logic:**
    *   Filter actions based on Trigger compatibility (e.g., standardizing data payloads).
    *   Selection moves to Preview.

### 3.4 Step 4: Preview & Save
*   **UI Reference:** Screen 8.
*   **Components:** Visual connector drawing a line between Trigger Icon and Action Icon.
*   **Logic:**
    *   Payload assembly: `{ name: string, triggerId: string, actionId: string, enabled: true }`.
    *   API Call: `POST /api/v1/workflows`.
    *   On Success: Navigate to History/Dashboard with specific transition animation.

## 4. Execution Monitoring

### 4.1 History View
*   **UI Reference:** Screen 9.
*   **Display:** Reverse chronological list.
*   **Status Mapping:**
    *   `SUCCESS`: Green Pill.
    *   `FAILED`: Red Pill.
    *   `PENDING`: Yellow/Gray Pill.
*   **Toggle Logic:** Immediate local UI update (Optimistic UI updates), followed by background API call `PATCH /workflows/{id}/toggle`. Revert UI if API fails.

## 5. User Profile

### 5.1 Settings Navigation
*   **UI Reference:** Screen 10.
*   **Logout:** Clears `EncryptedSharedPreferences` tokens, cancels all `WorkManager` jobs, navigates to Welcome Screen (Screen 1) clearing backstack.

## 6. Edge Cases
*   **Network Loss:** App functions in "Read-Only" mode. Creation wizard blocks at Step 4 if valid token cannot be verified, or queues creation request via WorkManager.
*   **Token Expiry:** 401 response triggers automatic refresh. If refresh fails, route to Login.

## 7. Screen Transitions
*   **Wizard Flow:** SlideInRight / SlideOutLeft.
*   **Back Navigation:** SlideInLeft / SlideOutRight.
*   **Modal Errors:** BottomSheet dialogs.