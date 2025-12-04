# Test Plan & Quality Assurance: FitLife Finance

## 1. Testing Strategy Pyramid
We adhere to the 70/20/10 split: 70% Unit Tests, 20% Integration Tests, 10% UI/E2E Tests.

### 1.1 Unit Testing (JUnit 5 + Mockk)
*   **Scope:** ViewModels, UseCases, Repository logic, Utility functions.
*   **Tools:** `JUnit 5`, `Mockk`, `kotlinx-coroutines-test`, `app.cash.turbine` (for Flow testing).
*   **Key Scenarios:**
    *   **Budget Logic:** Verify that `spent > budget` triggers the `OverBudget` state (Red flag).
    *   **Formatting:** specific specific locale tests for currency formatting.
    *   **ViewModel State:** Test `HomeViewModel` initial state -> Loading -> Success/Error.

### 1.2 Integration Testing (Hilt)
*   **Scope:** Room Database migrations, DAO queries, Retrofit parsing.
*   **Tools:** `Hilt Android Testing`.
*   **Example:** Verify that saving a Transaction into the DAO correctly updates the Flow observed by the Repository.

### 1.3 UI Testing (Compose + Espresso)
*   **Scope:** Screen rendering, Navigation flows, Interactive elements.
*   **Tools:** `Compose Test Rule`, `Espresso`.
*   **Scenarios:**
    *   **Onboarding:** Click "Get Started" -> Verify Navigates to Sign Up.
    *   **Add Expense:** Open Modal -> Click Save with Amount 0 -> Verify Error State.
    *   **Cards:** Verify 4 items are displayed in the LazyColumn.

## 2. Example Test Cases

### TC-AUTH-001: Login Validation
*   **Input:** Empty email, correct password.
*   **Expected:** Sign In button does not submit; Email field shows "Required" error.

### TC-DASH-001: Balance Hiding
*   **Action:** User taps the "Eye" icon on the Balance Card.
*   **Expected:** Text changes from "$24,562.80" to "$**,***.**".

### TC-BUD-001: Over Budget Visuals
*   **Pre-condition:** Entertainment budget = $300, Spent = $380.
*   **Action:** Open Budget Screen.
*   **Expected:** Entertainment Progress bar is Red; Text says "Over budget!".

## 3. CI/CD Integration
*   **Bitrise / GitHub Actions:** 
    *   On PR Open: Run `./gradlew testDebugUnitTest` + Lint.
    *   On Merge to Main: Run `./gradlew connectedAndroidTest` (Firebase Test Lab) -> Build Release AAB.