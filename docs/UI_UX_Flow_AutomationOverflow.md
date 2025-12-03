# UI/UX Flow & Inventory: Automation Overflow

## 1. Design System Overview
Based on visual analysis, the app uses a custom Dark Mode theme.

*   **Background:** `#0A0A14` (Deep Navy/Black)
*   **Surface/Cards:** `#141423` (Dark Blue Gray)
*   **Primary CTA Gradient:** Linear Gradient (Start: `#7F00FF` Violet, End: `#00D4FF` Cyan)
*   **Typography:** Sans-serif (Resembling standard Roboto/Product Sans).

## 2. Screen Inventory & Layout Specs

### Screen 1: Splash / Welcome
*   **Route:** `Screen.Onboarding`
*   **Components:**
    *   `Box` (FillMaxSize): Background.
    *   `Column` (Centered): App Icon (200dp), Title "Automation Overflow".
    *   `Button` (Primary Gradient): "Get Started".
    *   `TextButton`: "Log In".

### Screen 2: Log In
*   **Route:** `Screen.Login`
*   **Components:**
    *   `TopAppBar`: Navigation Icon (Back).
    *   `Column`: Title "Log In".
    *   `OutlinedTextField`: "Email Address" (InputType.Email).
    *   `OutlinedTextField`: "Password" (VisualTransformation.Password).
    *   `Text`: "Forgot password?" (Clickable).
    *   `Button`: "Log In" (Full width).

### Screen 3: Create Automation (Naming)
*   **Route:** `Screen.CreateWorkflow.Name`
*   **Components:**
    *   `OutlinedTextField`: "Name your skill". FocusRequester enabled on entry.
    *   `Button`: "Continue".

### Screen 4 & 6: Choose Trigger
*   **Route:** `Screen.CreateWorkflow.TriggerSelect`
*   **Components:**
    *   `LazyColumn`: Vertical list.
    *   **Item:** `Card` (Row: Icon, Column(Title, Subtitle)).
    *   **Data Layout:** Image/Icon sizes fixed at 40dp.

### Screen 5 & 7: Select Action
*   **Route:** `Screen.CreateWorkflow.ActionSelect`
*   **Components:**
    *   Identical structure to Trigger Select.
    *   Includes `Modifier.navigationBarsPadding()` for the bottom button container.

### Screen 8: Workflow Preview
*   **Route:** `Screen.CreateWorkflow.Preview`
*   **Components:**
    *   `Column`: Summary of selections.
    *   **Visual Connector:** A vertical line drawing between Trigger Card and Action Card to imply flow.
    *   `Button`: "Save Automation".

### Screen 9: Execution History
*   **Route:** `Screen.History`
*   **Components:**
    *   `LazyColumn`: List of past runs.
    *   **Status Badge:** Pill shape. Success = Green text/bg. Running = Yellow text/bg.
    *   **Toggle:** Read-only toggle indicating if the workflow is currently active.

### Screen 10: Profile
*   **Route:** `Screen.Profile`
*   **Components:**
    *   **Header:** `Row` (Avatar CircleImage, Column(Name, Email)).
    *   **Menu:** `Column` of Settings Items (Icon + Text + Chevron).
    *   **Footer:** `Button` (Outlined/Destructive color) "Log Out".

## 3. Navigation Graph (Compose)

```kotlin
NavHost(navController, startDestination = Screen.Onboarding) {
    composable<Screen.Onboarding> { OnboardingScreen(...) }
    composable<Screen.Login> { LoginScreen(...) }
    
    navigation<Graph.CreateWorkflow>(startDestination = Screen.Name) {
        composable<Screen.Name> { ... }
        composable<Screen.TriggerSelect> { ... }
        composable<Screen.ActionSelect> { ... }
        composable<Screen.Preview> { ... }
    }
    
    composable<Screen.History> { HistoryScreen(...) }
    composable<Screen.Profile> { ProfileScreen(...) }
}
```