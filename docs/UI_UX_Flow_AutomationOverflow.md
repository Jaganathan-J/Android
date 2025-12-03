# UI/UX Flow & Design System

### 1. Screen Inventory & Flow Map

**A. Onboarding Module**
*   **S01: Welcome Screen:**
    *   *Elements:* Gradient background overlay or image, Large Icon, Title "Automate your life", Subtitle, "Get Started" (Primary Btn), "Log In" (Text Link).
    *   *Action:* Get Started -> S02 (Register Mode implied); Log In -> S02.
*   **S02: Login Screen:**
    *   *Elements:* Field: Email, Field: Password, "Forgot password?", "Log In" (Primary Btn).
    *   *Action:* Success -> S09 (History).

**B. Creation Module (The Wizard)**
*   **S03: Name Workflow:**
    *   *Elements:* Input field "Name your automation", "Continue" (Primary Btn).
    *   *Action:* Continue -> S04.
*   **S04: Choose Trigger (List A):**
    *   *Elements:* List of integration cards (Icon + Title + Desc).
    *   *Action:* Tap Item -> S05.
*   **S05: Select Action (Grid):**
    *   *Elements:* 2-column Grid of action cards.
    *   *Action:* Tap Item -> S08.
*   (Alternative Paths: S06 Trigger List B and S07 Action List are alternate UI variants for A/B testing).

**C. Review & Commit**
*   **S08: Preview:**
    *   *Elements:* Visual chain: [Trigger Icon] -> [Arrow] -> [Action Icon]. Text summary.
    *   *Action:* "Save Automation" -> POST API -> Navigate to S09.

**D. Main Extension (Home)**
*   **S09: Execution History:**
    *   *Elements:* List of items. Each item has title, time, status badge, toggle. FAB (Floating Action Button) or Header Button to "Create New".
    *   *Action:* Tap item -> Details. Toggle -> Enable/Disable.

**E. Settings**
*   **S10: Profile:**
    *   *Elements:* Avatar, Name, List Menu (Account, Notifs, Appearance), Log Out.
    *   *Action:* Log Out -> Clear Token -> Navigate to S01.

### 2. Design System Tokens (Compose)

#### Typography (Type.kt)
```kotlin
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif, // Replace with custom font from resources
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )
    // ... define all styles matching wireframes
)
```

#### Formatting & Colors (Color.kt)
*   **Background:** `#0A0A16` (Midnight)
*   **Surface:** `#14142B`
*   **PrimaryBrush:** `Brush.horizontalGradient(colors = listOf(Color(0xFF8A2BE2), Color(0xFF4169E1)))`
*   **TextPrimary:** `#FFFFFF`
*   **TextSecondary:** `#A0A3BD`

### 3. Components

*   **`GradientButton`:** A Composable accepting `onClick` and `text`. Applies the PrimaryBrush to the background shape `RoundedCornerShape(8.dp)`.
*   **`InputTextField`:** Custom implementations of `OutlinedTextField` with `#14142B` container color and no visible border until focused.
*   **`IntegrationCard`:** Accepts `iconUrl`, `title`, `subtitle`. Layout: Row (List) or Column (Grid).

### 4. Accessibility Guidelines
*   **Semantics:** All graphic elements (Screen 1 Icon, Screen 10 Avatar) must have `contentDescription`.
*   **State descriptions:** The toggle in Screen 9 must use `stateDescription` to announce "Automation [Name] is On" or "Off".
*   **Focus Order:** Ensure the keyboard `ImeAction.Next` moves focus from Name -> Continue button, or Email -> Password.
