### Test Plan

#### Unit Test Coverage
- All ViewModels, Use Cases, Adapters, and Data Sources.
- Mock dependencies using Mockito or Hilt Testing.
- Test cases for edge scenarios (empty fields, invalid inputs).

#### UI Tests
- ComposeTest to verify UI components and interactions.
- Robolectric tests for Activity/Fragment behavior.
- E2E testing for the entire workflow creation flow.

#### Example Test Cases
1. **Login Flow:** Test successful and failed login attempts.
2. **Workflow Creation:** Test name validation, trigger selection, action selection, and save functionality.
3. **Execution History:** Verify display of past executions with different statuses.
4. **Profile Management:** Test profile editing and secure logout.

#### Manual Testing
- Conducted in beta phases to gather user feedback.
- Focus on usability, performance, and bug detection.