# Test Plan - FitLife Finance

## 1. Testing Strategy
We employ the **Testing Pyramid**: lots of Unit Tests, moderate Integration Tests, and targeted UI Tests.

## 2. Unit Tests (JUnit 5 + Mockk)
*   **Target:** 80% Code Coverage on Domain and Data layers.
*   **ViewModels:** Test strict StateFlow emission sequences using `app.cash.turbine:turbine`.

### Example: DashboardViewModelTest
```kotlin
@Test
fun `when dashboard loads, uiState updates to Success`() = runTest {
    // Presented with mock data
    coEvery { useCase() } returns Result.success(mockSummary)
    
    // When ViewModel inits
    val viewModel = DashboardViewModel(useCase)
    
    // Then
    viewModel.uiState.test {
        val loading = awaitItem()
        assert(loading.isLoading)
        
        val success = awaitItem()
        assertEquals(mockSummary.totalBalance, success.totalBalance)
    }
}
```

## 3. UI Tests (Compose Testing)
*   **Framework:** `androidx.compose.ui:ui-test-junit4`
*   **Scenarios:**
    1.  **Dashboard Render:** Verify "Total Balance" text exists.
    2.  **Navigation:** Verify tapping "Add Expense" opens the form.
    3.  **Input Validation:** Verify entering "0" in Amount keeps "Save" button disabled.

### Example: ExpenseScreenTest
```kotlin
@get:Rule
val composeTestRule = createAndroidComposeRule<ComponentActivity>()

@Test
fun enterAmount_enablesButton() {
    composeTestRule.setContent { AddExpenseScreen(...) }
    
    composeTestRule.onNodeWithText("Save Expense").assertIsNotEnabled()
    
    composeTestRule.onNodeWithText("Amount").performTextInput("10.00")
    
    composeTestRule.onNodeWithText("Save Expense").assertIsEnabled()
}
```

## 4. Manual Test Cases
| ID | Feature | Step | Expected Result |
|----|---------|------|-----------------|
| TC01 | Onboarding | Launch App | Gradient Splash shown, "Get Started" visible |
| TC02 | Expenses | Add -$50 Expense | Dashboard balance decreases by $50 |
| TC03 | Budget | Spend > Budget | Progress bar turns Red (if logic implemented) |