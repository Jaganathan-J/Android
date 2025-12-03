# Functional Specification Document (FSD): Automation Overflow

**Start Date:** October 26, 2025
**Dependencies:** Android SDK 35, Jetpack Compose 1.8

## 1. Introduction
This document details the functional behavior of the Automation Overflow Android application, encompassing the logic, data flow, and UI states derived from the visual analysis of Screens 1 through 10.

## 2. User Stories & Acceptance Criteria

### US-01: User Onboarding & Login
**As a** new user,
**I want** to log in securely,
**So that** I can access my private automations.

*   **Sources:** Screen 1 (Splash), Screen 2 (Log In).
*   **Acceptance Criteria:**
    1.  Splash screen must display "Get Started" and "Log In" options.
    2.  Login form validates Email format regex.
    3.  Password field masks input characters.
    4.  "Log In" button (Gradient CTA) is disabled until fields are non-empty.
    5.  Successful login navigates to Home/Dashboard (implied) or Create Flow.

### US-02: Workflow Creation - Initiation
**As a** user,
**I want** to name my new automation,
**So that** I can identify it later.

*   **Sources:** Screen 3 (Create Automation).
*   **Acceptance Criteria:**
    1.  Screen displays input field for "Name your skill".
    2.  "Continue" button is disabled if input is empty.
    3.  Tapping "Continue" navigates to Trigger Selection.

### US-03: Trigger & Action Selection
**As a** user,
**I want** to choose disparate services to connect,
**So that** I can bridge workflows.

*   **Sources:** Screen 4/6 (Triggers), Screen 5/7 (Actions).
*   **Acceptance Criteria:**
    1.  Displays a vertical list of available integration cards.
    2.  Each card shows Icon, Title, and Description.
    3.  Tapping a card highlights the selection (visual feedback).
    4.  Selection immediately transitions to the next step (Trigger -> Action, Action -> Preview).

### US-04: Workflow Confirmation
**As a** user,
**I want** to review my configuration,
**So that** I ensure the logic is correct before activating.

*   **Sources:** Screen 8 (Preview).
*   **Acceptance Criteria:**
    1.  Display the User-defined Name (from Screen 3).
    2.  Display selected Trigger card.
    3.  Display selected Action card.
    4.  "Save Automation" button commits data to the backend API via POST.

### US-05: Monitoring History
**As a** user,
**I want** to see past execution logs,
**So that** I know if my automations are working.

*   **Sources:** Screen 9 (Execution History).
*   **Acceptance Criteria:**
    1.  LazyColumn list of history items.
    2.  Badge indicates status: "Success" (Green/Blue) or "Failed" (Red).
    3.  Pull-to-refresh functionality to update the list.

## 3. Feature Flows & Logic

### 3.1 The "Wizard" Logic State Machine
The creation flow utilizes a `SharedViewModel` across the Wizard Activity/NavGraph.

**State Object:**
```kotlin
data class WorkflowDraft(
    val name: String = "",
    val triggerId: String? = null,
    val actionId: String? = null
)
```

**Transitions:**
1.  **Draft Init:** User taps "+" (implied) or "Create".
2.  **Naming:** User inputs text. Validation: `text.length > 0`.
3.  **Trigger:** User taps list item. State updates `triggerId`. Navigates to `route_select_action`.
4.  **Action:** User taps list item. State updates `actionId`. Navigates to `route_preview`.
5.  **Commit:** User taps "Save". ViewModel calls `Repository.createWorkflow(draft)`.

### 3.2 Error Handling
*   **Network Errors:** Display a `Snackbar` with "Retry" action if the API fails during Login or Save.
*   **Validation Errors:** Inline red helper text on Layout Screen 2 (Login) for invalid emails.
*   **Empty States:** If Screen 9 (History) is empty, display an illustration "No runs yet".

## 4. Screen Transitions
*   **Standard Forward:** Slide In from Right.
*   **Back Navigation:** Slide Out to Right (Standard Android Predictive Back Gesture supported).
*   **Modal:** Error dialogs appear as overlay Modals.

## 5. Persistence
*   **Session Token:** EncryptedSharedPreferences (MasterKey alias).
*   **Drafts:** Saved in `SavedStateHandle` to survive process death during creation.
*   **Cache:** Room Database caches Execution History for offline viewing.