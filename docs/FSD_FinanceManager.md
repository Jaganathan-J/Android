# Functional Specification Document (FSD): FinanceManager

## 1. Introduction
This document details the functional requirements for the FinanceManager Android application. It maps the visual elements from the wireframe analysis to specific engineering logic descriptions.

## 2. Authentication Module
**Ref:** Screen 1 (Welcome), Screen 2 (Log In)

### 2.1 logic
*   **Input Validation:**
    *   `Email`: Regex validation for standard email format.
    *   `Password`: Min 8 chars, at least 1 special char.
*   **State Management:**
    *   Button "Log In" (Screen 2) remains `disabled` until validation passes.
    *   On API Loading: Button shows `CircularProgressIndicator`, text hidden.
    *   On Error: Snackbar appears anchored to bottom (e.g., "Invalid Credentials").
*   **Navigation:**
    *   Success -> Navigate to `DashboardGraph` (Home).
    *   Forgot Password -> Navigate to deep link web view or reset flow.

## 3. Workflow Builder Module (Core)
**Ref:** Screens 3, 4, 5, 6, 7, 8

### 3.1 Create Automation (Start)
*   **Screen:** Screen 3.
*   **UI Element:** Text Field "Nume any kills" (Corrected to "Name your Skill/Automation").
*   **Logic:**
    *   Auto-focus keyboard on entry.
    *   "Continue" button anchored to IME (keyboard) visibility or bottom of screen.
    *   User cannot proceed without a non-empty string.

### 3.2 Trigger Selection
*   **Screen:** Screen 4 / Screen 6.
*   **Data Source:** `GET /api/integrations/triggers`.
*   **Interaction:**
    *   List is a `LazyColumn`.
    *   Single selection logic.
    *   Tap -> Save selection to `WorkflowBuilderViewModel` state -> Navigate to Action Selection.

### 3.3 Action Selection
*   **Screen:** Screen 5 / Screen 7.
*   **Data Source:** `GET /api/integrations/actions`.
*   **Context Awareness:** The list of available actions may filter based on the selected Trigger (compatibility check).
*   **Interaction:** Similar `LazyColumn` selection. Tap -> Update State -> Navigate to Preview.

### 3.4 Workflow Preview Visualization
*   **Screen:** Screen 8.
*   **Rendering Logic:**
    *   Dynamic drawing of a connector line between the Trigger Icon and Action Icon.
    *   Canvas or Custom Composable `WorkflowNodeView`.
    *   **Icons:** Must load asynchronously (Coil/Glide) from URLs provided by the backend integration metadata.
*   **Finalize:**
    *   Button: "Save Automation".
    *   Action: `POST /api/workflows` with JSON payload `{ name, triggerId, actionId }`.
    *   On Success: Pop back stack to Home/Dashboard.

## 4. Monitoring & History Module
**Ref:** Screen 9 (Execution History)

### 4.1 List Display
*   **Component:** `LazyColumn` with `Card` items.
*   **Data:** Paged List (`Paging 3` library) from `GET /api/history`.
*   **Elements per Row:**
    *   **Badge:** Map backend status `"SUCCESS"` to Color Green, `"FAILED"` to Color Red, `"PENDING"` to Color Yellow.
    *   **Toggle:** Active/Inactive state for the automation definition itself. 
    *   **Logic:** Toggling the switch triggers `PATCH /api/workflow/{id}/status` immediately (Optimistic UI update).

## 5. User Profile Module
**Ref:** Screen 10 (Profile Settings)

### 5.1 Read View
*   Display User Avatar, Name, Email from local `UserSession` (DataStore/Room).

### 5.2 Settings Navigation
*   List items (Account, Notifications) are simple navigation triggers.
*   **Log Out:**
    *   Action: Clear `DataStore` tokens. Clear `Room` database tables (sensitive finance info).
    *   Navigation: Pop entire backstack, route to `Screen 1 (Welcome)`.

## 6. Edge Cases & Error Handling
*   **Network Failure:** All list screens (Triggers/History) must show a "Retry" button and Empty State illustration if the network is down.
*   **Typographical Errors:** The underlying code must correct the UI labels observed in screenshots (e.g., change "Atcion" to "Action" in string resources) to ensure professional release quality.