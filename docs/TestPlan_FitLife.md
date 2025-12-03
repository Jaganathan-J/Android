# Test Plan & Quality Assurance

## 1. Testing Strategy
We employ the **Testing Pyramid**: extensive Unit Tests, moderate Integration Tests, and targeted UI Automations.

## 2. Unit Testing (JUnit 5 + Mockk)
*   **Scope:** ViewModels, UseCases, Repositories, Utility classes.
*   **Coverage Target:** 80% code coverage.
*   **Example Case (ViewModel):**
    *   *Scenario:* `LoginViewModel` should emit `Loading` then `Success` when repo returns valid token.
    *   *Mock:* `AuthRepository.login()` returns `Result.Success`.

## 3. UI/Instrumentation Testing (Compose Test Rule)
*   **Scope:** Critical user flows (Login, Create Workflow).
*   **Tools:** Espresso, Compose Test Rule.
*   **Example Case (Creation Flow):**
    1.  Launch `InitiateScreen`.
    2.  Assert "Continue" button is disabled.
    3.  Enter text "Test Workflow".
    4.  Assert "Continue" button is enabled.
    5.  Perform Click.
    6.  Assert Navigation to `TriggerSelectScreen`.

## 4. Manual / visual QA
*   **Dark Mode Verification:** Ensure no black text on dark backgrounds.
*   **Gradient Check:** Verify gradient buttons render correctly on older Android versions (API 26).
*   **Typo Correction:** Verify all typos in original wireframes ("Contiate", "Sance") are corrected in the build.

## 5. Performance Testing
*   **Tools:** Android Profiler (Memory, CPU, Energy).
*   **Stress Test:** Load `Execution History` with 1000 items to verify `LazyColumn` recycling performance.