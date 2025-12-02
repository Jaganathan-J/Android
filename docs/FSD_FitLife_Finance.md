# Functional Specification Document (FSD)
## Application: FitLife Finance

### 1. Introduction
This document details the functional behavior of the FitLife Finance Android application. It serves as the bridge between the high-level PRD and the technical implementation, ensuring all screens from the wireframe analysis are functionally described.

### 2. User Stories & Acceptance Criteria

#### US-01: User Onboarding & Authentication
**Description:** As a user, I want to securely log in so that my financial data is protected.
**Screen Reference:** Splash Screen, Login Screen.
**Flow:**
1. App Launch -> Splash Screen shows "Finance Manager" logo -> User taps "Get Started".
2. User lands on Login Screen.
3. User enters Email and Password -> Taps "Log in".
**Acceptance Criteria:**
- Verify email format (`Regex`).
- Verify password not empty.
- Show loading spinner on "Log in" button press.
- On success, navigate to Dashboard (clear back stack).
- On failure, show Snackbar with error.

#### US-02: Dashboard Overview
**Description:** As a user, I want to see my balance and recent activity immediately.
**Screen Reference:** Dashboard (Main), Dashboard (Alternative).
**Flow:**
1. Dashboard loads `Total Balance` from local DB.
2. `Spending Report` bar chart renders last 7 days of data.
3. `Recent Transactions` list shows top 5 entries descending by date.
**Acceptance Criteria:**
- Top Bar displays "Dashboard".
- Tapping "Recent Transaction" item navigates to Transaction Details (implied).
- Tapping "Add Expense" button (floating or header) navigates to Add Expense Screen.
- Toggle button (referenced as "LOKOM/Add Expense" in analysis) switches logic view between Timeframes (e.g., Weekly/Monthly).

#### US-03: Transaction Logging
**Description:** As a user, I want to record expenses/income.
**Screen Reference:** Add Expense, Add Income, Expense Categories.
**Flow:**
1. User taps "Add Expense".
2. User enters Amount (Keyboard numeric).
3. User selects Date (DatePickerDialog defaults to Today).
4. User taps Category -> Navigates to/Dialog for "Expense Categories".
5. User selects "Shopping"/"Housing".
6. User taps "Add Expense" button.
**Edge Cases:**
- Inputting 0 or negative amounts -> Block and show error field.
- No category selected -> Button disabled or validation error.

#### US-04: Analysis & Budgeting
**Description:** As a user, I need to see where my money goes.
**Screen Reference:** Monthly Insights, Budget Planner.
**Flow:**
1. User navigates to Insights.
2. Donut chart renders proportional segments based on Category totals.
3. Legend displays Income (Green), Expense (Red), Balance (Blue).
4. User navigates to Budget Planner.
5. Progress bar shows current spend vs. Goal amount.

### 3. Screen Transitions & Navigation Path

The app utilizes a **Hub-and-Spoke** navigation model with a nested Graph.

- **Auth Graph:**
  - `splash_route` -> `login_route`
- **Main Graph:**
  - `dashboard_route` (Home)
  - `transactions_route` (List)
  - `add_transaction_route/{type}` (Argument: Expense/Income)
  - `insights_route`
  - `profile_route`
  - `settings_route`
  - `budget_planner_route`

Navigate Back behavior: Standard Android back stack. Creating a transaction finishes that activity/screen and pops back to Dashboard, triggering a data refresh.

### 4. Validation Logic

**Financial Input Field:**
- Max length: 12 digits.
- Decimals: Limited to 2 places.
- Filter: No special characters except currency symbol (visual only) and decimal separator.

**Category Selection:**
- Must retrieve list from Room DB `Category` table.
- Default categories: Shopping, Housing, Car, Food, Entertainment.

### 5. Error Handling
- **Network Error:** If syncing (future feature), show "Offline Mode" banner.
- **Database Error:** If Room insert fails, show "Could not save transaction" Toast and log to Crashlytics.
- **UI Feedback:** All destructive actions (Delete Transaction) require a confirmation Dialog.