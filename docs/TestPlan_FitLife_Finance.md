# Test Plan
## Application: FitLife Finance

### 1. Testing Strategy Pyramid
- **70% Unit Tests:** Domain logic, ViewModels, Repositories, Mappers.
- **20% Integration Tests:** Room Database DAO tests, ViewModel+Repository integration.
- **10% UI/E2E Tests:** Critical user journeys via Compose UI Tests.

### 2. Unit Testing (JUnit 5 + Mockk)
**Scope:** Verify business logic without Android dependencies.

**Example: `CalculateBudgetUseCase`**
- Input: List of Transactions, Budget Limit.
- Output: Remaining Balance, Percentage Used.
- **Test Case:** Ensure expenses subtract from limit, income adds to balance (if applicable).

**Tools:**
- `Mockk`: For mocking Repositories.
- `Turbine`: For testing `StateFlow` emissions in ViewModels.
- `kotlinx-coroutines-test`: For controlling time (`StandardTestDispatcher`).

### 3. UI Testing (Compose Test Rule)
**Scope:** Verify visual elements and navigation.

**Scenario 1: Add Expense Flow**
1. `composeTestRule.onNodeWithText("Add Expense").performClick()`
2. `composeTestRule.onNodeWithTag("input_amount").performTextInput("50")`
3. `composeTestRule.onNodeWithText("Save").performClick()`
4. Verify navigate back to Dashboard.

**Tools:**
- `androidx.compose.ui.test`
- Hilt Testing (`@HiltAndroidTest`) to inject in-memory databases.

### 4. Integration Testing (Room)
**Scope:** Verify SQL queries.
- Create an in-memory `RoomDatabase`.
- Insert a `Transaction`.
- Query `getRecentTransactions()`.
- **Assert:** The item returned matches inserted data and is sorted correctly by Date DESC.

### 5. Manual QA Checklist
- **Edge Case:** App backgrounding while adding expense (state restoration).
- **Edge Case:** Changing system Dark Mode toggle (app theme response).
- **Edge Case:** Inputting extremely large numbers (layout overflow check).