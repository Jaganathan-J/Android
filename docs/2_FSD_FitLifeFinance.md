# Functional Specification Document (FSD): FitLife Finance

## 1. User Stories & Acceptance Criteria

### 1.1 Authentication & Onboarding
**US-AUTH-01: New User Onboarding**
*   **Story:** As a new user, I want to see a clear value proposition and a "Get Started" button so that I understand the app's purpose.
*   **Screen:** `S00_Onboarding`
*   **Acceptance Criteria:**
    *   Display "FinanceManager" title and "Track, Budget, Save" pills.
    *   "Get Started" navigates to Registration.
    *   "Sign In" navigates to `S01_SignIn`.

**US-AUTH-02: Secure Sign In**
*   **Story:** As a registered user, I want to sign in with email/password or social providers.
*   **Screen:** `S01_SignIn`
*   **Acceptance Criteria:**
    *   Validate Email format (regex).
    *   Mask Password input with toggle visibility icon.
    *   "Forgot Password" initiates recovery flow.
    *   Social buttons (Google/Facebook) trigger OAuth.

### 1.2 Dashboard & Navigation
**US-DASH-01: View Total Financial Status**
*   **Story:** As a user, I want to see my total balance and recent trends immediately upon login.
*   **Screen:** `S02_Home`
*   **Acceptance Criteria:**
    *   Display Total Balance (e.g., "$24,562.80") with a "Hide" toggle.
    *   Show trend indicator (e.g., "+12.5% from last month").
    *   Show top 3 recent transactions.
    *   Display Notification badge count on the bell icon.

**US-DASH-02: Quick Actions**
*   **Story:** As a user, I want one-tap access to common tasks.
*   **Screen:** `S02_Home`
*   **Acceptance Criteria:**
    *   Four buttons: Send, Request, Add, More.
    *   "Add" triggers `S07_AddExpense` modal.

### 1.3 Transaction Management
**US-TRX-01: Browse Transaction History**
*   **Story:** As a user, I want to filter and search my full transaction history.
*   **Screen:** `S06_Transactions`
*   **Acceptance Criteria:**
    *   Search bar filters list by Merchant Name.
    *   Filter chips (All/Income/Expense) toggle list visibility.
    *   Group items by Date (Today, Yesterday, Specific Date).
    *   Income shown in Green (+), Expenses in Red (-).

**US-TRX-02: Add Manual Transaction**
*   **Story:** As a user, I want to manually record a cash expense.
*   **Screen:** `S07_AddExpense`
*   **Acceptance Criteria:**
    *   Modal presentation.
    *   Input Amount (Numeric keypad).
    *   Select Category (Dropdown).
    *   Select Payment Source (Dropdown showing current balance).
    *   Save button disabled until validation passes (> $0).

### 1.4 Budgeting & Analytics
**US-BUD-01: Monitor Category Budgets**
*   **Story:** As a user, I want to see if I am over-spending in specific categories.
*   **Screen:** `S05_Budget`
*   **Acceptance Criteria:**
    *   Visual progress bar for each category.
    *   Calculate % spent.
    *   If consumed > 100%, display "(Over budget!)" in red.
    *   Navigation between months via arrow controls.

**US-ANL-01: Visualize Spending**
*   **Story:** As a user, I want graphical breakdown of my finances.
*   **Screen:** `S03_Analytics`
*   **Acceptance Criteria:**
    *   Bar chart for weekly spending trends.
    *   Donut chart for categorical breakdown.
    *   Toggle between Week/Month/Quarter/Year views.

### 1.5 Account Management
**US-ACC-01: View Linked Accounts**
*   **Story:** As a user, I want to see balances of all linked bank cards.
*   **Screen:** `S04_Accounts`
*   **Acceptance Criteria:**
    *   List individual cards (Chase, BoA, Wells Fargo).
    *   Show status (Active vs. Due in 5 days).
    *   Total aggregated balance at the top.

## 2. Feature Flows & Logic

### 2.1 The "Add Expense" Logic Flow
1. User taps "Add" FAB.
2. `AddExpenseSheet` opens.
3. **Validation:** 
    *   `Amount` != null && `Amount` > 0.
    *   `Category` != null.
    *   `Date` <= Today.
4. On Save: Dispatch `CreateTransactionUseCase`.
5. On Success: Close sheet, refresh `HomeViewModel` and `BudgetViewModel` to reflect new balance and budget utilization.

### 2.2 Budget Calculation Logic
*   **Total Budget:** Sum of all category limits.
*   **Remaining:** `Total Budget` - `Sum(Transactions in Current Period)`.
*   **Status:**
    *   Green: < 80% utilized.
    *   Orange: 80% - 99% utilized.
    *   Red: >= 100% utilized.

## 3. Validations & Error Handling
*   **Network Errors:** All screens fetching data must handle `Result.Error` by showing a Snackbar with "Retry" action.
*   **Input Format:** 
    *   Currency inputs must prevent multiple decimals.
    *   Search inputs must debounce for 300ms.