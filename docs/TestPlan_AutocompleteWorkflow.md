# Test Plan & Strategy

## 1. Testing Pyramid Strategy
*   **Unit Tests (70%):** Business logic, ViewModels, Mappers.
*   **Integration Tests (20%):** Repositories (Room + Fake API), UseCase chains.
*   **UI/E2E Tests (10%):** Critical user journeys (Login, Create Workflow).

## 2. Unit Testing Specification
*   **Tools:** JUnit 5, Mockk, Cortana Test, Turbine (for Flow testing).
*   **Example (ViewModel):**
    ```kotlin
    @Test
    fun `when trigger selected, update state`() = runTest {
        viewModel.onEvent(CreateEvent.SelectTrigger(mockTrigger))
        viewModel.uiState.test {
            val item = awaitItem()
            assert(item.selectedTrigger == mockTrigger)
        }
    }
    ```

## 3. UI Testing (Screens 3-8)
*   **Tools:** Compose UI Test (Junit4), Espresso (Legacy system interaction).
*   **Scenario: Create Workflow Happy Path**
    1.  Launch `CreateWorkflowActivity`.
    2.  Wait for `NameInput` tag.
    3.  Enter "Test Workflow".
    4.  Click "Continue".
    5.  Assert `TriggerSelectionList` is displayed.
    6.  Perform click on first item.
    7.  Assert `ActionSelectionList` is displayed.

## 4. Snapshot Testing
*   **Tool:** Paparazzi.
*   **Goal:** Prevent visual regressions on pixel-perfect screens like Screen 8 (Workflow Preview) and Screen 9 (History Cards).
*   **Configuration:** Capture snapshots for Dark Mode, Light Mode, and 200% Font Scale.

## 5. Manual QA Checklist
*   **Connectivity:** Test app behavior when Airplane Mode is toggled during the "Save Automation" step.
*   **Auth:** Verify session persistence after app kill/restart.
*   **Scrolling:** Ensure 60fps scrolling on Execution History with > 100 items.