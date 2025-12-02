# UI/UX Navigation & Screen Inventory

## 1. Global Navigation Strategy
FitLife uses a **Stack-based navigation** model managed by `Jetpack Navigation Compose`. The application does not use a Bottom Navigation Bar, relying instead on a central "Hub" (Profile Page) to branch out to features. This mimics a focused, task-oriented UX.

## 2. Screen Inventory & Hierarchy

### Group 1: Onboarding & Auth
*   **S01: Welcome Screen**
    *   *Route:* `onboarding/welcome`
    *   *Elements:* Full-screen Purple bg, Illustration, "Get Started" CTA.
*   **S02: Login Screen**
    *   *Route:* `auth/login`
    *   *Elements:* Email (`OutlinedTextField`), Password, Log In (`Button`), Forgot Password (`TextButton`).

### Group 2: Core Experience (The Hub)
*   **S03: Profile / Main Menu**
    *   *Route:* `home/profile`
    *   *Components:* `CircleAvatar`, User Name, Vertical Menu List (`Workouts`, `Exercises`, `Progress`, `Settings`).
    *   *Action:* Acts as the implementation of the Dashboard.

### Group 3: Feature - Workouts
*   **S05: Exercise Categories**
    *   *Route:* `workout/categories`
    *   *Layout:* `LazyVerticalGrid` (2 columns).
    *   *Items:* "Jump Rope", "Deadlift", etc.
*   **S06: Category Details**
    *   *Route:* `workout/list/{categoryId}`
    *   *Layout:* `LazyColumn` listing routines.
*   **S07: Routine Details**
    *   *Route:* `workout/detail/{routineId}`
    *   *Layout:* List of specific exercises (Squats, Pushups).

### Group 4: Feature - Execution
*   **S08: Active Progress**
    *   *Route:* `execution/active/{exerciseId}`
    *   *Components:* Custom Canvas (Circular Progress), Big Timer Text, `Row` of Buttons (Pause, Skip).
    *   *Transition:* Hero transition from list item.
*   **S09: Summary**
    *   *Route:* `execution/summary`
    *   *Components:* "Good Job" header, Stats, Done button.

### Group 5: Feature - Settings
*   **S04: Settings Menu**
    *   *Route:* `settings/main`
    *   *Items:* Notification, Reminders, Units, Privacy Policy.

## 3. Visual Language (Material 3)
*   **Primary Color:** Deep Indigo (`#3A3A7A`) - Used in Headers, Titles.
*   **Secondary Color:** Coral / Orange (`#F05A4A`) - Action Buttons (Pause, Get Started).
*   **Tertiary Color:** Lavender (`#D1C4E9`) - Secondary buttons (Skip), Card Backgrounds.
*   **Typography:** Sans-serif (Roboto/Inter). Bold Headers, legible body text.