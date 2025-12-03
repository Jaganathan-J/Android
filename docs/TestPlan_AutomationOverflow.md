# Comprehensive Test Plan

## 1. Testing Strategy
We adhere to the **Testing Pyramid**: heavy on Unit Tests, moderate on Integration, light on UI/E2E.

## 2. Unit Testing (JUnit 5 + Mockk)
*   **Scope**: ViewModels, UseCases, Repositories, Utility classes.
*   **Coverage Target**: 80% Line Coverage.
*   **Example (ViewModel)**:
    ```kotlin
    @Test
    fun `when submit login with valid creds, state becomes Authenticated`() = runTest {
        coEvery { loginUseCase(any(), any()) } returns Result.success(user)
        viewModel.handleIntent(LoginIntent.Submit("a@b.com", "pass"))
        assertTrue(viewModel.uiState.value.isAuthenticated)
    }
    ```

## 3. UI Testing (Compose Test Rule)
*   **Scope**: Custom Composables, Screen Layouts.
*   **Tooling**: Combined with Hilt Testing for dependency replacement.
*   **Scenario**: **"Create Automation Flow"**
    1.  Launch `CreateAutomationScreen`.
    2.  Input "Test Bot" into Name field.
    3.  Perform Click on "Continue".
    4.  Verify Navigation to `ChooseTriggerScreen`.

## 4. Integration Testing
*   **Scope**: Database migrations (Room), API parsing (Retrofit).
*   **Tooling**: MockWebServer to simulate API responses ensuring correct JSON parsing.

## 5. Manual QA Checklist
*   [ ] Verify Dark Mode contrast in direct sunlight.
*   [ ] Test scrolling behavior in History with 100+ items.
*   [ ] Verify soft keyboard behavior on Login screen (does it obscure the button?).