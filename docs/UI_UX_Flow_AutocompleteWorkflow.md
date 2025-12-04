# UI/UX Flow & Design Specifications

## 1. Design System Overview
Based on the analysis, the app utilizes a high-contrast Dark Mode theme suitable for technical professionals.

### 1.1 Color Palette (Material 3 Token Mapping)
*   `md.sys.color.surface`: #0A0A14 (Deep Blue/Black Background)
*   `md.sys.color.surfaceContainer`: #141422 (Card Background)
*   `md.sys.color.primary`: #5E5CE6 (Purple Gradient Start)
*   `md.sys.color.onPrimary`: #FFFFFF
*   `md.sys.color.outline`: #2C2C3E (Borders)
*   `md.sys.color.error`: #FF453A
*   `md.sys.color.tertiary`: #28C76F (Success Green)

### 1.2 Typography (Type Scale)
*   **Display Large:** 32sp, Bold (Screen 1 Title)
*   **Headline Medium:** 24sp, SemiBold (Screen Headers)
*   **Title Medium:** 18sp, Medium (Card Titles)
*   **Body Medium:** 14sp, Regular (Descriptions)
*   **Label Large:** 14sp, Medium (Buttons)

## 2. Screen Inventory & Composable Definitions

### 2.1 Onboarding
*   **Screen 1: WelcomeScreen**
    *   `WelcomeHeroImage()`: Vector illustration.
    *   `PrimaryButton(text = "Get Started")`.
    *   `SecondaryTextButton(text = "Log In")`.

### 2.2 Authentication
*   **Screen 2: LoginScreen**
    *   `StandardTopBar(title = "Log In")`.
    *   `AuthTextField(label = "Email")`.
    *   `AuthTextField(label = "Password", isSecure = true)`.

### 2.3 Creation Flow (Wizard)
*   **Screen 3: NameSetupScreen**
    *   `InputSpacer`: Weights content to center-top.
    *   `ValidationButton(enabled = name.isNotEmpty())`.
*   **Screen 4 & 6: TriggerSelectScreen**
    *   `IntegrationCard(icon, title, description, onClick)`.
    *   `LazyColumn`: For efficient list rendering.
*   **Screen 5 & 7: ActionSelectScreen**
    *   Reuses `IntegrationCard` component.
*   **Screen 8: PreviewScreen**
    *   `ConnectionVisualizer(trigger, action)`: Custom Canvas drawing a dashed line between icons.

### 2.4 Management
*   **Screen 9: HistoryScreen**
    *   `HistoryItem(status, name, timestamp, toggleState)`.
    *   Status badge uses `AssistChip` styling.
*   **Screen 10: ProfileScreen**
    *   `ProfileHeader(avatar, name, email)`.
    *   `SettingsMenuItem(icon, label)`.

## 3. Navigation Graph
```kotlin
Sealed Class AppRoutes {
  data object Welcome : AppRoutes("welcome")
  data object Login : AppRoutes("login")
  data object Dashboard : AppRoutes("dashboard") // Houses History
  data object CreateFlow : AppRoutes("create_flow") // Nested Graph
  data object Profile : AppRoutes("profile")
}
```

## 4. Accessibility Implementation
*   **Semantics:** All `IntegrationCard` composables must have `contentDescription` detailing the trigger name.
*   **Dynamic Type:** All text sizes use `sp` units to respect system font scaling.