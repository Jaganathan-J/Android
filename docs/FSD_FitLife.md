# Functional Specification Document (FSD): FitLife

## 1. Introduction
This document details the functional behavior of the FitLife Android application. It serves as the source of truth for engineering implementation using Jetpack Compose and Clean Architecture.

## 2. User Stories & Acceptance Criteria

### 2.1 Feature: Authentication
**Story:** As a returning user, I want to log in securely so I can access my personalized data.
*   **Inputs:** Email text field, Password text field (masked).
*   **Action:** Tap "Log In" button.
*   **Validation:** 
    *   Email regex check.
    *   Password min-length check.
    *   Button disabled until validity met.
*   **Success:** Navigate to `ProfileScreen`.
*   **Error:** Toast message "Invalid Credentials" on 401 response.

### 2.2 Feature: Navigation Hub (Profile)
**Story:** As a user, I want a central menu to access different modes.
*   **UI:** List of 4 distinct cards: Workouts, Exercises, Progress, Settings.
*   **Behavior:**
    *   "Workouts" -> Navigates to `CategoryListScreen`.
    *   "Settings" -> Navigates to `SettingsScreen`.
*   **Ref:** Screen 3 (Profile).

### 2.3 Feature: Active Workout Session
**Story:** As a user, I want to perform an exercise with a visual timer.
*   **UI:** Large countdown text (e.g., "01:24"), Circular Progress Indicator, Pause (Orange), Skip (Purple).
*   **Logic:**
    *   Timer ticks down 1s per second.
    *   Progress ring depletes counter-clockwise.
    *   **Pause:** freezes timer.
    *   **Skip:** ends current exercise, moves to next in queue.
*   **Edge Case:** Screen rotation must NOT reset timer (handled via `ViewModel` `SavedStateHandle`).
*   **Backgrounding:** Timer must continue in a Foreground Service if app is minimized.

## 3. Feature Flows

### 3.1 Workout Execution Flow
1.  **Select Category** (Screen 5) -> Select specific Routine (Screen 6).
2.  **Load Exercise List** (Screen 7).
3.  **Press Start** (Implicit).
4.  **Active Screen** (Screen 8) loop: 
    *   *While (Time > 0):* Update UI.
    *   *If (Pause):* Stop tick.
    *   *If (Skip/Complete):* Load next exercise.
5.  **Completion:** Navigate to Summary (Screen 9).

## 4. Error Handling
*   **Network:** Offline-first architecture. If API fails, serve cached workout definitions from Room DB.
*   **Permissions:** Request Notification permission (Android 13+) for workout timer alerts in Settings flow.