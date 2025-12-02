# Functional Specification Document (FSD) - FitLife Finance

**Version:** 1.0.0
**Framework:** Android Native (Kotlin/Compose)

## 1. Functional Overview
This document details the logic, validation, and behavior of the FitLife application. The app utilizes a Single Activity Architecture with a Composable navigation graph. The navigation model is **Hub-and-Spoke**, centering on the Dashboard.

## 2. User Stories & Acceptance Criteria

### 2.1. Authentication & Onboarding
**User Story:** As a user, I want to see a welcoming splash screen so that I appreciate the brand before logging in.
*   **Ref Screen:** Splash / Onboarding Welcome Screen.
*   **Flow:** App Launch -> Display Splash -> User taps "Get Started" -> Navigate to Dashboard (Mock Auth for MVP).
*   **Gradient Logic:** Background must render a vertical brush gradient (Teal #00C6FF to Purple #0072FF).

### 2.2. Dashboard (The Hub)
**User Story:** As a user, I want to see my total balance and spending immediately.
*   **Ref Screen:** Dashboard / Home Screen.
*   **Elements:**
    *   `TotalBalanceCard`: Displays aggregated sum formatted as currency (e.g., "$5,234.50").
    *   `SpendingMonthCard`: Displays current month's outflow.
    *   `YourCardsSection`: Horizontal preview of linked cards.
*   **Logic:**
    *   On fast-scroll, numbers should retain state.
    *   Data is fetched via `DashboardRepository.getSummary()`.

### 2.3. Expense Entry
**User Story:** As a user, I want to record a cash expense quickly.
*   **Ref Screen:** Add Expense.
*   **Input Fields:**
    *   `Amount`: Numeric Keypad. Validates > 0. Format to 2 decimal places.
    *   `Category`: Dropdown/Modal. Options: Food, Transport, Entertainment, Shopping.
    *   `Description`: Free text (Cap at 100 chars).
    *   `Date`: DatePicker Dialog. Defaults to `Instant.now()`.
*   **Validation:**
    *   If Amount is empty/0 -> Disable "Save Expense" button.
    *   If Category is null -> Show error.
*   **Post-Condition:** Save to Room DB; Navigate back to specific caller (Dashboard or Transaction List).

### 2.4. Transaction History
**User Story:** I want to review my past spending.
*   **Ref Screen:** Transactions Screen.
*   **Behavior:**
    *   Infinite scroll list (`LazyColumn`).
    *   Negative amounts formatted in Red.
    *   Positive amounts (Refunds/Income) formatted in Green.
    *   Group by Date (Today, Yesterday, etc. - *Assumed enhancement*).

### 2.5. Budget Management
**User Story:** I want to see if I am overspending on food.
*   **Ref Screen:** Monthly Budget.
*   **Logic:**
    *   Calculation: `(Spent / Limit) * 100`.
    *   Visual: `LinearProgressIndicator`.
    *   If Value > 80% -> Indicator turns Yellow.
    *   If Value > 100% -> Indicator turns Red.

## 3. Navigation Logic
Since no bottom navigation bar is present in the wireframes (per analysis), the app uses a centralized navigation pattern:
*   **Home:** Dashboard.
*   **Drill-downs:** Tapping cards on Dashboard leads to specific screens (e.g., Tapping "Spending" -> Analytics; Tapping "Balance" -> Accounts).
*   **Modals:** "Add Expense" is presented as a full-screen modal route.
*   **Back Handling:** System Back gesture handles strict stack popping.

## 4. Error Handling
*   **Network Failure:** Show `SnackbarHost` message "Offline Mode - Data cached".
*   **API 500:** Retry mechanism with exponential backoff.
*   **Form Errors:** Inline text below `OutlinedTextField` in Red.