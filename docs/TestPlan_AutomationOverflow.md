# Test Plan: Automation Overflow

## 1. Testing Strategy
We adhere to the **Testing Pyramid**: High volume of Unit Tests, moderate Integration Tests, and targeted UI/E2E Tests.

## 2. Unit Testing (JUnit 5 + Mockk)
Target Coverage: 80%+

### 2.1 ViewModels
*   **Scope:** `CreateWorkflowViewModel`.
*   **Test Case:** Verify `uiState` updates when `selectTrigger(id)` is called.
*   **Test Case:** Verify `SaveAutomation` calls repository and handles Exception fallback.

### 2.2 UseCases
*   **Scope:** `ValidateLoginUseCase`.
*   **Test Case:** `isValidEmail("plainstring")` returns `false`.
*   **Test Case:** `isValidEmail("user@domain.com")` returns `true`.

## 3. UI Testing (Compose Test Rule)

### 3.1 Screen Tests
*   **Tool:** `androidx.compose.ui.test`
*   **Scenario (Screen 2):**
    ```kotlin
    @Test
    fun loginButton_disabled_initially() {
        composeTestRule.setContent { LoginScreen(...) }
        composeTestRule.onNodeWithText("Log In").assertIsNotEnabled()
    }
    ```
*   **Scenario (Screen 4):** Verify list contains Triggers and they are clickable.

### 3.2 Navigation Tests
*   Verify creating a workflow moves from `Name` -> `Trigger` -> `Action` -> `Preview`.

## 4. Integration Testing (Hilt + Robolelectric)
*   **Scope:** Testing the real Room Database alongside Repository logic.
*   **Test:** Saving a Workflow in Repository correctly writes to DAO.

## 5. Manual QA Checklist
1.  **Visual:** Verify the Gradient Button color (#7F00FF to #00D4FF) matches Figma specs on OLED screens.
2.  **Edge Case:** Try creating an automation with a 200-character name (Screen 3).
3.  **Network:** Toggle Airplane mode while on Screen 9 (History) and ensure cached data loads.
4.  **Auth:** Verify "Log Out" on Screen 10 clears SharedPreferences and redirects to Splash.