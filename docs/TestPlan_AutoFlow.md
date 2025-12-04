# Test Plan Strategy

## 1. Overview
We aim for the "Testing Pyramid": 70% Unit Tests, 20% Integration Tests, 10% E2E UI Tests.

## 2. Unit Testing (JVM)
*   **Tools:** JUnit 5, Mockk, Turbine (for Flows).
*   **Coverage Target:** 80% of Domain and ViewModel layers.

### Example: ViewModel Test
```kotlin
@Test
fun `when login succeeds, state updates to Success`() = runTest {
    // Given
    coEvery { authRepo.login(any(), any()) } returns Result.success(user)
    
    // When
    viewModel.attemptLogin("test@test.com", "pass")

    // Then
    viewModel.uiState.test { 
        assert(awaitItem() is LoginState.Loading)
        assert(awaitItem() is LoginState.Success)
    }
}
```

## 3. UI Testing (Instrumented)
*   **Tools:** Compose Test Rule, Espresso.
*   **Scenarios:**
    1.  **Creation Flow:** Verify user can tap through Screens 3 -> 4 -> 5 -> 8.
    2.  **Navigation:** Verify Back button preserves state in the wizard.
    3.  **Appearance:** Verify critical UI elements exist (buttons, text) via Semantics.

### Example: Compose Test
```kotlin
@get:Rule
val composeTestRule = createAndroidComposeRule<MainActivity>()

@Test
fun workflow_wizard_flow() {
    composeTestRule.onNodeWithText("Create Automation").performClick()
    composeTestRule.onNodeWithTag("input_name").performTextInput("My Bot")
    composeTestRule.onNodeWithText("Continue").assertIsEnabled()
}
```

## 4. Manual / QA Script
1.  **Login Typo Check:** Enter invalid email, verify error message appears.
2.  **Long Text Test:** Enter 100 chars in Automation Name, verify truncation or limit.
3.  **Offline Mode:** Turn off Wifi, open History, verify Cached data loads or "Offline" snackbar appears.
4.  **Typo verification:** Ensure the legacy typo "Sance" is GONE and "Contiate" is corrected to "Create" in the final build.