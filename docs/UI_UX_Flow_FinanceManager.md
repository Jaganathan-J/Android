# UI/UX Flow & Design System: FinanceManager

## 1. Visual Language
Based on the visual analysis, the app utilizes a "Cyber-Corporate" aesthetic.

*   **Theme:** `Theme.FinanceManager.Dark` (System Dark Mode forced or default).
*   **Background Color:** `Color(0xFF0A0A14)` (Deep Void).
*   **Surface Color:** `Color(0xFF141422)` (Card Background).
*   **Primary Gradient:** `Brush.horizontalGradient(0xFF3B82F6, 0xFF8B5CF6)` (Blue to Purple).
*   **Typography:**
    *   **Headings:** `FontFamily.SansSerif` (Bold), White.
    *   **Body:** `FontFamily.SansSerif` (Medium/Regular), Color(0xFF9CA3AF).

## 2. Screen Inventory & Logic Map

### A. Entry Cluster
*   **Route:** `auth_graph`
*   **S1 Welcome:**
    *   Box inputs: None.
    *   Action: Buttons.
*   **S2 Login:**
    *   `TextField` (Email), `TextField` (Password, `visualTransformation = PasswordVisualTransformation`).
    *   TopBar: Transparent, Back Navigation.

### B. Workflow Creator Cluster
*   **Route:** `builder_graph`
*   **S3 Naming:**
    *   `Scaffold` -> `Column`.
    *   FocusRequester on the Input Field.
*   **S4/S6 Trigger List:**
    *   layout: `LazyColumn`.
    *   Item: `IntegrationCard(icon: Painter, title: String)`.
*   **S5/S7 Action List:**
    *   layout: `LazyColumn`.
    *   Item: `IntegrationCard`.
*   **S8 Preview:**
    *   Layout: `Column(Arrangement.SpaceBetween)`.
    *   Center Component: `WorkflowVisualizer` (Custom Canvas drawing a line between two circle-clipped images).

### C. Dashboard Cluster
*   **Route:** `main_graph`
*   **S9 Execution History:**
    *   Status Badges: Custom Composable `StatusChip(status: String)`.
        *   Uses conditional background color logic.
*   **S10 Profile:**
    *   `Column` scrollable.
    *   Avatar: `CircleShape` clip.

## 3. Navigation Graph (Compose)

```kotlin
NavHost(navController, startDestination = "auth_flow") {
    navigation(startDestination = "welcome", route = "auth_flow") {
        composable("welcome") { WelcomeScreen(...) }
        composable("login") { LoginScreen(...) }
    }
    navigation(startDestination = "name_input", route = "builder_flow") {
        composable("name_input") { AutomationNameScreen(...) }
        composable("trigger_select") { TriggerSelectScreen(...) }
        composable("action_select") { ActionSelectScreen(...) }
        composable("preview") { WorkflowPreviewScreen(...) }
    }
    navigation(startDestination = "history", route = "dashboard") {
        composable("history") { HistoryScreen(...) }
        composable("profile") { ProfileScreen(...) }
    }
}
```

## 4. Component Specifications

### Primary Button
*   **Height:** 56.dp
*   **Shape:** `RoundedCornerShape(12.dp)`
*   **Background:** Linear Gradient.
*   **Text Style:** H6, White, SemiBold.

### Input Fields
*   **Container:** Dark Grey `Color(0xFF1F1F2E)`.
*   **Border:** 1.dp Solid `Color(0xFF2D2D3F)`.
*   **Label:** External, Top-Start aligned.