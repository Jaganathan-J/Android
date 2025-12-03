# UI/UX Flow & Design System

## 1. Global Styles
*   **Background:** Immersive dark mode. No white backgrounds permitted.
*   **Gradients:** Use `Brush.horizontalGradient(colors = list Of(Purple, Pink))` for Primary Buttons.
*   **Icons:** Use Material Icons Rounded or filled variants.

## 2. Screen Inventory & Layout Specs

### 2.1 Screen 1: Splash/Onboarding
*   **Layout:** `Box` with centered Logo. `Column` at bottom for buttons.
*   **Transition:** Fade in Logo -> Slide up Buttons.

### 2.2 Screen 2: Log In
*   **Layout:** `Column` (vertical scroll).
*   **Components:** 
    *   `OutlinedTextField` (Custom styled: dark fill, no border until focused).
    *   `GradientButton` (Text: "Log In").

### 2.3 Screen 3: Initiate Automation
*   **Layout:** Top-aligned form.
*   **Header:** "Initiate Automation".
*   **Input:** Single large text field. Placeholder "Name your workflow".

### 2.4 Screen 4/6: Choose Trigger
*   **Layout:** `Scaffold` with `TopAppBar`. `LazyColumn` content.
*   **Cards:** Height 80dp. Left Icon (24dp) in colored container (40dp). Text vertically centered.

### 2.5 Screen 8: Workflow Preview
*   **Layout:** Vertical Step list.
*   **Connector:** Vertical line drawn using `Canvas` or `Divider` between Trigger Card and Action Card.

### 2.6 Screen 9: Execution History
*   **List Item:** Row layout.
    *   Left: Workflow Name (Bold).
    *   Subtitle: Time elapsed.
    *   Right: `Switch` (Thumb color: White, Track color: Green/Grey).

## 3. Navigation Map
1.  **Start** -> Splash
2.  Splash -> Login (if no token) OR Dashboard (if token)
3.  Dashboard (FAB add button) -> Initiate Workflow
4.  Initiate -> Trigger List
5.  Trigger List -> Action List
6.  Action List -> Preview
7.  Preview -> Save -> Dashboard

## 4. Accessibility (A11y)
*   **Touch Targets:** All interactive elements must be min 48dp x 48dp.
*   **Content Description:** All icons must have meaningful descriptions (e.g., `contentDescription = "Back"`).
*   **Text Scale:** UI must adapt to System Font Scale up to 200%.