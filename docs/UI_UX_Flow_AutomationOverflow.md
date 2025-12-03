# UI/UX Flow & Design System

## 1. Design Tokens (Material 3)
Based on visual analysis, the app uses a custom high-contrast dark theme.

### Colors
*   `md_theme_dark_background`: #0A0A16
*   `md_theme_dark_surface`: #1C1C2D (Cards/Inputs)
*   `md_theme_dark_primary`: Brush Gradient (Start: #3B82F6, End: #8B5CF6)
*   `md_theme_dark_onPrimary`: #FFFFFF
*   `md_theme_dark_error`: #CF6679
*   `md_theme_dark_success`: #10B981

### Typography
*   **Font Family**: Inter (Google Fonts).
*   `DisplayMedium`: 24sp, Bold (Page Titles).
*   `TitleMedium`: 18sp, SemiBold (Card Titles).
*   `BodyLarge`: 16sp, Regular (Inputs, List items).
*   `LabelLarge`: 14sp, Medium (Buttons).

## 2. Screen Inventory & Layout Specs

### S1: Welcome / Onboarding
*   **Layout**: `Column(Arrangement.Center)`.
*   **Elements**: App Logo, Large H1 "Automate Your Life", Primary Button "Get Started", Text Button "Log In".

### S2: Log In
*   **Layout**: `Scaffold` with TopBar.
*   **Content**: `Column`. `OutlinedTextField` (Custom styled, no borders, bg #1C1C2D) for Email/Pass. 12dp rounded corners.
*   **CTA**: Primary Gradient Button.

### S3: Create Automation (Wizard Step 1)
*   **TopBar**: Back Arrow, "Create Automation".
*   **Body**: Single Input Field "Name". Auto-focus on entry.

### S4 & S5: Trigger/Action Selection (Wizard Steps 2 & 3)
*   **List**: `LazyColumn`.
*   **Item**: `Surface` (Card) with Row inside. [IconBox (40dp)] - [Text Column].
*   **IconBox**: Rounded square with tint color.

### S6: Preview
*   **Layout**: Timeline connector view. Vertical dashed line connecting Trigger Card and Action Card.

### S7: Execution History
*   **List**: `LazyColumn`.
*   **Item**: Row. [Text: Workflow Name] + [Spacer] + [StatusIcon].
*   **StatusIcon**: Custom composable rendering a green toggle or red alert icon.

### S8: Profile
*   **Header**: CircleImage (80dp) + Name (H2).
*   **Menu**: Column of Settings Items (Notification, Privacy, etc.).

## 3. Accessibility (A11y)
*   All interactive elements min size 48x48dp.
*   `contentDescription` mandatory on all Icons.
*   Text fields require `label` or `placeholder` accessible via TalkBack.
*   Color contrast ratios validated for WCAG 2.1 AA.