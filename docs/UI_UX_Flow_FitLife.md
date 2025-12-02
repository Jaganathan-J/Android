# UI/UX Design Guidelines & Flow - FitLife Finance

## 1. Design System (Material 3 Customization)

### 1.1. Color Palette
Derived from Wireframe Gradient Analysis:
*   **Primary Gradient:** Vertical Brush (`Brush.verticalGradient`) 
    *   Top: `#0093E9` (Teal/Blue)
    *   Bottom: `#80D0C7` or `#4A00E0` (Deep Purple/Blue based on screens).
*   **Surface:** Transparent/Glassmorphic with White Borders.
*   **OnSurface (Text):** `#FFFFFF` (White High Emphasis).
*   **Accent/CTA:** `#00C6FF` (Cyan/Bright Blue button fills as seen in "Add Account").
*   **Error:** `#FF5252` (Red text in Transactions).

### 1.2. Typography
*   **Font Family:** Roboto (Android Default) or Inter (Custom).
*   **Display Large:** 32sp, Bold (e.g., "$5,234.50").
*   **Hausline Medium:** 20sp, SemiBold (e.g., "Monthly Budget").
*   **Body Large:** 16sp, Regular (e.g., Card usage).

## 2. Screen Inventory & Composition

### 2.1. Screen: Splash
*   **Route:** `Screen.Splash`
*   **Composition:** Box with Gradient Background. Column (Centered) -> Icon -> Title "Finance App" -> Subtitle -> Button "Get Started" (Bottom aligned).

### 2.2. Screen: Dashboard
*   **Route:** `Screen.Dashboard`
*   **Composition:** LazyColumn.
    *   `item`: HeaderRow (Greeting + Time).
    *   `item`: TotalBalanceCard (Aspect Ratio 16:9).
    *   `item`: SpendingCard (Row layout).
    *   `item`: YourCardsSection (Horizontal pager).

### 2.3. Screen: Add Expense
*   **Route:** `Screen.AddExpense`
*   **Composition:** Column.
    *   TopBar (Title "Add Expense").
    *   CardContainer -> Column -> 4x OutlinedTextField (White borders).
    *   Spacer (Weight 1f).
    *   CTA Button "Save Expense".

### 2.4. Screen: Transactions
*   **Route:** `Screen.Transactions`
*   **Composition:** LazyColumn.
    *   `items(transactionList)` -> TransactionRowItem.
    *   **TransactionRowItem:** Row -> Name/Category (Column) + Spacer + Amount (Red/Green).

### 2.5. Screen: Monthly Budget
*   **Route:** `Screen.Budget`
*   **Composition:** Column -> Header -> Total Budget Card -> Category List.
    *   **Category Item:** `Column` -> `Row(Label, Value)` + `LinearProgressIndicator(progress = value/total)`.

## 3. Accessibility Guidelines
*   **Content Description:** `Icons.Default.MoreVert` must have `contentDescription = "Options"`.
*   **Contrast:** Ensure the "Red" expense text on the Blue/Purple gradient passes WCAG AA (4.5:1). If not, lighten the background behind list items using `Surface(color = Color.Black.copy(alpha = 0.2f))`.
*   **Scalable Text:** All text size must use `sp` units.