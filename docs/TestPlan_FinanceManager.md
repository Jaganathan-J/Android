# Test Plan & Quality Assurance

## 1. Testing Strategy
We adhere to the **Testing Pyramid**: 70% Unit Tests, 20% Integration Tests, 10% UI/E2E Tests.

## 2. Unit Testing (JUnit 5 + Mockk)
*   **Scope:** ViewModels, UseCases, Repository Logic, Utility classes.
*   **Coverage Target:** 80% code coverage.
*   **Example Case (BudgetViewModel):**
    *   *Scenario:* Fetch budget returns Over Budget category.
    *   *Assertion:* ViewModel state maps `status="OVER_BUDGET"` to `Color.Red`.

## 3. Integration Testing (Robolectric)
*   **Scope:** Room Database DAO operations, Local/Request Mappers.
*   **Example Case:**
    *   Save a Transaction to DAO -> Query DAO -> Verify data persistence.

## 4. UI Testing (Compose Test Rule)
*   **Scope:** Screen composables, navigation, user interactions.
*   **Tools:** Espresso, Compose UI Test JUnit4.
*   **Scenario: Add Expense Flow (SCR-05)**
    ```kotlin
    @Test
    fun addExpense_enablesSaveButton_onlyWhenValid() {
        composeTestRule.setContent { AddExpenseScreen() }
        // Button should be disabled initially
        composeTestRule.onNodeWithText("Save Expense").assertIsNotEnabled()
        // Enter amount
        composeTestRule.onNodeWithText("0.00").performClick().performTextInput("20.00")
        // Create category
        composeTestRule.onNodeWithText("Category").performClick()
        composeTestRule.onNodeWithText("Food").performClick()
        // Verify button enabled
        composeTestRule.onNodeWithText("Save Expense").assertIsEnabled()
    }
    ```

## 5. Manual QA Scenarios
1.  **Visual Regression:** Verify "Over Budget" red state in Budget Plan renders correctly on small screens (Pixel 5) vs large screens (Pixel 9 Pro).
2.  **Edge Case:** Attempt to add an expense with $0.00 amount. Ensure validation blocks it.
3.  **Network:** Enable "Airplane Mode" and verify cached data loads on Home Dashboard.