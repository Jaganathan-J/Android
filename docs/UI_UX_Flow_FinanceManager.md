# UI/UX Flow & Navigation Map

## 1. Global Navigation Paradigm
The app utilizes a **single-activity architecture** with a nested navigation graph.

*   **Root Graph:**
    *   `OnboardingRoute` (Start)
    *   `AuthRoute`
    *   `MainAppRoute` (Protected)

*   **MainAppRoute (Scaffold with BottomBar):**
    *   `Home` (Tab 1)
    *   `Analytics` (Tab 2)
    *   `Cards` (Tab 3)
    *   `Budget` (Tab 4)
    *   `Profile` (Tab 5)

## 2. Screen Details & Transitions

### 2.1 Onboarding & Auth
*   **Screen:** Onboarding (SCR-01)
    *   *Action:* Tap "Get Started" -> Slide Transition -> Sign In (SCR-02).
*   **Screen:** Sign In (SCR-02)
    *   *Action:* Tap Back -> Slide Pop -> Onboarding.
    *   *Action:* Tap "Sign In" -> Fade/Replace -> Home Dashboard.

### 2.2 Dashboard (SCR-03)
*   **Layout:** CoordinatorLayout behavior with Collapsing Toolbar logic for the greeting header.
*   **Lists:** `LazyRow` for Quick Actions, `LazyColumn` for Transactions.
*   **Transition:** Tap Account Card -> Expense Shared Element Transition -> Account Details.

### 2.3 Budget Planner (SCR-07)
*   **Visuals:** Custom `Canvas` drawing for progress bars to handle multi-color segments (Green/Orange/Red).
*   **Gestures:** Horizontal swipe on top Month Selector to change periods.

### 2.4 Add Expense (SCR-05)
*   **Presentation:** `ModalBottomSheet` (standard Material 3 behavior).
*   **Animation:** Slides up from bottom, dims background 50%.
*   **Keyboard:** `imePadding` modifier required to push content up when entering Amount/Notes.

### 2.5 Analytics (SCR-08)
*   **Interactivity:** Tapping a bar in the bar chart highlights it and updates the specific day's total in a tooltip overlay.

## 3. Design System (Material 3)

### 3.1 Color Palette (Extracted from Analysis)
*   `Primary`: Green #4CAF50
*   `Error`: Red #E53935
*   `Surface`: White #FFFFFF
*   `OnSurface`: Dark Charcoal #212121
*   `Warning`: Orange/Amber for "Near Budget"

### 3.2 Typography
*   `DisplayLarge`: Balance Amount ($24,562.80)
*   `HeadlineMedium`: Screen Titles (Transactions, Profile)
*   `LabelSmall`: Tab bar labels, Axis labels