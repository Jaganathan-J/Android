# Test Plan & Quality Assurance

## 1. Overview
This plan outlines the testing strategy to ensure FitLife meets the rigorous quality standards of a modern Android application. We employ the Testing Pyramid: heavy on Unit Tests, moderate Integration Tests, and targeted UI Tests.

## 2. Unit Testing (JUnit 5 + Mockk)
*   **Scope:** Domain Layer (Use Cases), Data Layer (Repositories), and Utility classes.
*   **Coverage Goal:** 80% Line Coverage.
*   **Sample Case (Timer Logic):**
    *   *Scenario:* Timer decrements correctly.
    *   *Test:* Set timer to 60s, advance clock 1s, assert current value is 59s.

## 3. UI Testing (Compose UI Test + Espresso)
*   **Tool:** `androidx.compose.ui.test`
*   **Key Scenarios:**
    *   **Login Flow:** Verify error text appears when "Log In" is clicked with empty fields.
    *   **Navigation:** verify clicking "Workouts" on Profile screen (Screen 3) leads to Categories screen (Screen 5).
    *   **Semantics:** Verify the "Back" arrow has content description "Go Back" for accessibility.

## 4. Integration Testing
*   **Hilt Testing:** Verify proper injection of components in a test harness.
*   **Room + Flow:** Verify that inserting a workout into the DB triggers the Flow collector in the ViewModel.

## 5. Manual QA Checklist
*   **Device Fragmentation:** Test on Pixel 9 (Android 15), Samsung S24, and a low-end device (Android 10).
*   **Edge Cases:**
    *   Rotate device while Timer (Screen 8) is running. Timer must not reset.
    *   Receive a phone call during a workout.
    *   Airplane mode logic for "Offline-First" data fetching.