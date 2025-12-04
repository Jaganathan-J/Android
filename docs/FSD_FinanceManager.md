# Functional Specification Document (FSD): FinanceManager

## 1. Authentication Flows
### 1.1 Sign In (SCR-02)
*   **User Story:** As a user, I want to log in using email or social accounts so my data is synced.
*   **Pre-conditions:** App is in unauthenticated state.
*   **Flow:** User enters Email/Password -> Taps "Sign In".
*   **Validation:** Email regex validation; Password non-empty check.
*   **Error Handling:** Invalid credentials display inline error below fields.
*   **Post-condition:** Navigate to Dashboard (SCR-03).

## 2. Financial Management Flows
### 2.1 Dashboard & Aggregation (SCR-03)
*   **Logic:** System MUST fetch balances from all linked accounts. "Total Balance" = Sum(Checking + Savings) - Sum(Credit Debt).
*   **Interactions:** Tapping "See All" navigates to Transaction History (SCR-06).
*   **Visibility:** User can toggle balance visibility (eye icon).

### 2.2 Add Expense Logic (SCR-05)
*   **User Story:** As a user, I want to quickly record a cash lunch purchase.
*   **Input Fields:** Amount (Decimal), Category (Dropdown), Pay From (Account Selector), Date/Time (Pickers), Recurring (Switch).
*   **Recurring Logic:** If "Recurring" is TRUE, backend creates a CRON job for the transaction.
*   **Validation:** Amount > 0.01; Category must be selected.

### 2.3 Budget Enforcement (SCR-07)
*   **Logic:** For every category (e.g., Entertainment), Calculate `Total_Spent`. 
    *   `IF Total_Spent > Budget_Limit`: Set UI state to `Error` (Red progress bar, "Over budget!" text).
    *   `IF Total_Spent > 0.8 * Budget_Limit`: Set UI state to `Warning` (Orange).
    *   `ELSE`: Set UI state to `Safe` (Blue/Green).
*   **Data Source:** Aggregation of current month's transactions filtered by CategoryID.

## 3. Account Management (SCR-04)
*   **Display:** List linked accounts with dynamic status colors (Red for Debt/Due dates, Green for Active).
*   **Action:** "+ Link New Account" initiates external banking SDK (simulated).

## 4. Analytics & Reporting (SCR-08)
*   **Charts:** Bar chart renders `Sum(Transaction.Amount)` grouped by `DayOfWeek`.
*   **Filters:** Segmented control toggles query parameters (Week/Month/Quarter).
*   **Donut Chart:** Renders top 5 categories by percentage; aggregates remaining into "Other".

## 5. User Profile & Settings (SCR-10)
*   **Toggle:** "Dark Mode" switch immediately invalidates current `Theme` config and recomposes UI.
*   **Sign Out:** Clears `EncryptedSharedPreferences`, cancels all `WorkManager` sync jobs, navigates to Onboarding (SCR-01).