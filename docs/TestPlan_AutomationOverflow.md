# Test Plan & Strategy

### 1. Overview
We employ the **Testing Pyramid**: heavy on Unit Tests, moderate on Integration Tests, light on UI/E2E tests.

### 2. Unit Testing
*   **Scope:** ViewModels, UseCases, Repositories, Utility classes.
*   **Tools:** JUnit 5, Mockk, Turbine (for Flow testing), CoroutineTestDispatcher.
*   **Coverage Target:** 80% Line Coverage.

**Example: ViewModel Test (Login)**
```kotlin
@Test
fun `login success updates state`() = runTest {
    // Given
    coEvery { loginUseCase(any(), any()) } returns Result.Success(user)
    
    // When
    viewModel.handleEvent(LoginEvent.Submit("test@u.com", "pass"))
    
    // Then
    viewModel.uiState.test {
        assert(awaitItem() is LoginState.Loading)
        assert(awaitItem() is LoginState.Success)
    }
}
```

### 3. UI/Integration Testing through Compose
*   **Scope:** Verifying individual screens render correctly and basic interactions work.
*   **Tools:** Compose UI Test JUnit 4 integration.
*   **Scenario:** Ensure "Create Automation" button is disabled if Name is empty.

```kotlin
@get:Rule
val composeTestRule = createComposeRule()

@Test
fun createWorkflow_buttonDisabledInitially() {
    composeTestRule.setContent { CreateWorkflowScreen(...) }
    
    composeTestRule.onNodeWithText("Continue")
        .assertIsNotEnabled()
        
    composeTestRule.onNodeWithText("Name your automation")
        .performTextInput("My Auto")
        
    composeTestRule.onNodeWithText("Continue")
        .assertIsEnabled()
}
```

### 4. Manual QA Test Cases
*   **TC-01:** Install app fresh. Verify splash screen does not hang.
*   **TC-02:** Attempt Login with invalid email format. Verify visual feedback (if implemented) or button non-action.
*   **TC-03:** Create Automation flow: Select "Slack" as trigger, "Drive" as Action. Verify summary screen matches selection.
*   **TC-04:** History Scroll: Ensure list scrolls smoothly with 50+ items.
*   **TC-05:** Theme: Verify text is readable (White on Dark Blue) in a dark room environment.
