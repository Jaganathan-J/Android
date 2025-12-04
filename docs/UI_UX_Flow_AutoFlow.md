# UI/UX Flow & Screen Inventory

## 1. Design System Overview
*   **Theme:** Dark Mode Default (Brand: "Deep Space").
*   **Primary Color:** Gradient Linear(TopLeft #7F00FF -> BottomRight #007FFF).
*   **Surface Color:** Very Dark Grey/Blue (#0A0A14).
*   **Typography:** 'Inter' or 'Roboto'. Headings Bold, Body Regular.

## 2. Screen Inventory & Component Mapping

### Route: `auth_graph`
*   **Screen 1: Onboarding (`OnboardingScreen`)**
    *   *Components:* `Box` (Background Image), `Column` (Center), `Button` (Primary), `OutlinedButton` (Secondary).
    *   *Action:* "Get Started" -> `navController.navigate(Login)`.
*   **Screen 2: Log In (`LoginScreen`)**
    *   *Components:* `TextField` (Email, Pasword) with `VisualTransformation` for password, `ClickableText` (Forgot Password).
    *   *Transition:* Swipe Left to enter.

### Route: `creation_graph` (Wizard)
*   **Screen 3: Name Automation (`CreateNameScreen`)**
    *   *Components:* Large Header `Text`, Single `TextField`, `FloatingActionButton` or Bottom `Button` "Continue".
    *   *UX:* Auto-focus keyboard on entry.
*   **Screen 4/6: Trigger Selection (`SelectTriggerScreen`)**
    *   *Components:* `LazyColumn` containing custom `ServiceCard` composables.
    *   *Card Detail:* `Row` { Icon, `Column` { Title, Subtitle } }.
*   **Screen 5/7: Action Selection (`SelectActionScreen`)**
    *   *Components:* Reuse `SelectTriggerScreen` layout but bind to `ActionRepository`.
*   **Screen 8: Preview (`WorkflowPreviewScreen`)**
    *   *Components:* `ChainView` (canvas or custom column with vertical dotted line connectors), `Button` "Save Automation".

### Route: `main_graph`
*   **Screen 9: History (`HistoryScreen`)**
    *   *Components:* `LazyColumn` with `HistoryItem`.
    *   *HistoryItem:* `Card` containing Workflow Name, `StatusChip` (Green/Red), Timestamp, `Switch` (Enable/Disable).
*   **Screen 10: Profile (`ProfileScreen`)**
    *   *Components:* `CircleImage` (Avatar), `ListItem` (Settings options), `Button` (Log Out).

## 3. Navigation Map
```mermaid
graph TD
    A[Onboarding] --> B[Login]
    B --> C[Home (History)]
    C --> D[Create Wizard]
    D --> D1[Name]
    D1 --> D2[Select Trigger]
    D2 --> D3[Select Action]
    D3 --> D4[Preview]
    D4 -->|Save| C
    C --> E[Profile]
    E -->|Logout| B
```

## 4. Accessibility (A11y)
*   **Content Description:** All service icons must have dynamic descriptions (e.g., "Select Slack Trigger").
*   **Touch Targets:** All cards min-height 48dp.
*   **Color Contrast:** Secondary text (#A0A0B0) must be checked against background #14142B. Ensure ratio > 4.5:1. If not, lighten the gray.
*   **System Font Scale:** UI must respond to user font scaling preferences (use `sp` units, not `dp` for text).