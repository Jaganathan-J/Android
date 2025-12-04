# Test Plan: FinanceManager

## 1. Testing Strategy
We adopt the "Testing Pyramid": high volume of Unit Tests, moderate Integration Tests, and targeted UI/E2E Tests.

## 2. Unit Testing (JUnit 5 + MockK)
*   **Scope:** Domain UseCases, ViewModel State Logic, Utility classes.
*   **Coverage Target:** 80% line coverage.
*   **Example:** `ValidateEmailUseCaseTest`
    *   Input: "bad-email" -> Assert: Returns Error.
    *   Input: "alex@finance.com" -> Assert: Returns Success.

## 3. UI Testing (Compose Test Rule)
*   **Tool:** Jetpack Compose Testing API.
*   **Scope:** Verifying UI elements appear on screen and respond to click events.
*   **Screen 3 Test (Name Input):**
    ```kotlin
    @Test
    fun createAutomation_inputName_enablesButton() {
        composeTestRule.setContent { CreateAutomationScreen(...) }
        // Button should be disabled initially
        composeTestRule.onNodeWithText("Continue").assertIsNotEnabled()
        // Enter text
        composeTestRule.onNodeWithTag("name_input").performTextInput("My Bot")
        // Button should be enabled
        composeTestRule.onNodeWithText("Continue").assertIsEnabled()
    }
    ```

## 4. Integration Testing
*   **Tool:** Hilt + AndroidX Test.
*   **Scope:** Testing Database + Network + Repository integration.
*   **Scenario:** Fetch triggers from MockWebServer, save to Room, verify Flow emits data to ViewModel.

## 5. Manual QA Checklist
*   **Visual Inspection:** Verify Gradient colors match Figma visuals on physical devices (OLED panels).
*   **Edge Case:** Airplane mode handling on Screen 9 (History). App should show cached history.
*   **Typo Check:** Verify all typos seen in wireframes (e.g., "Sance", "Uptonked") are corrected to "Success" and "Uploaded".