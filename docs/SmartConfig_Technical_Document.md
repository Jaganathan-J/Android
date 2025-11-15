# 📱 Technical Design Document (TDD)

## Feature: Material 3 UI Component Library Integration

---

## 📌 Overview

This document outlines the implementation approach for integrating Material Design 3 (M3) UI components into an Android application using Clean Architecture principles. The goal is to deliver a modular, scalable, and highly testable system while leveraging modern Android best practices (Jetpack Compose, ViewModel, Navigation Component, Hilt for DI, etc.).

This implementation aligns with the Material 3 Design Kit’s guidance extracted from the Figma source document, covering M3 components such as App Bars, Buttons, Cards, Lists, Snackbar, Bottom Sheets, etc.

---

## 🔁 User Flow & Navigation

### Navigation Pattern

The user experience is structured based on a top-level scaffold with a Bottom Navigation Bar and a Navigation Drawer. Each navigation item leads to a feature screen (e.g., Components Gallery: Buttons, Cards, Snackbars).

### Sample Navigation Flow

```text
MainActivity (Scaffold)
 ├── HomeScreen 🏡
 │     └── FeaturedM3Components
 ├── ComponentsListScreen 📄
 │     ├── ButtonsDemoScreen
 │     ├── CardsDemoScreen
 │     ├── DialogsDemoScreen
 └── SettingsScreen ⚙️
```

---

## 🧩 UI Components & Layout Description

### System Layout Structure

```
Scaffold 
├── TopAppBar (Title, NavIcon, MenuIcon)
├── NavigationDrawer (optional)
├── BottomNavigationBar
└── ScreenContent (per route destination)
```

### Key Components

1. **App Bars**
   - TopAppBar (CenterAligned or SmallTopAppBar)
   - Optional menu overflow

2. **Buttons**
   - Filled, Elevated, Outlined, and Text Buttons
   - Show different states: idle, loading, disabled

3. **Cards**
   - Elevated and Filled cards with leading icon/image and text

4. **Lists**
   - One-line and two-line lists with supporting visuals

5. **Snackbar**
   - Dynamic messages with optional action

6. **Dialogs**
   - Support confirmation, warning, and input-based dialogs

7. **Chips**
   - Use for single-select and filters

8. **Bottom Sheets**
   - Modal and persistent sheet samples

---

## 🧠 State Management Logic (ViewModel Responsibilities)

Each demo screen will have a dedicated ViewModel managing:

- UI states (e.g., isLoading, errorMessage)
- Temporary UI interactions (e.g., button clicks, snackbar triggers)
- Navigation events (single-flow)
- Dynamic data since this is a component library (no real backend), data can be mocked

### Example ViewModel State

```kotlin
data class ButtonUiState(
    val isLoading: Boolean = false,
    val isEnabled: Boolean = true,
    val error: String? = null
)
```

Responsibilities include:

- Managing UI state and triggering recomposition
- Handling transient UI effects (snackbar messages, sheet interactions)
- Exposing state via StateFlow

---

## 🏗️ Repository + Use Case Structure

As the feature is primarily UI-driven (visual component demos), the clean architecture layers will still be adhered to for potential future scalability.

```
|-- domain
|    |-- models (e.g., SnackMessage)
|    |-- usecases (e.g., TriggerSnackbarUseCase)
|
|-- data
|    |-- repository (e.g., M3ComponentsRepositoryImpl)
|
|-- presentation
     |-- viewmodels (per screen)
     |-- uistates (per screen)
```

### Sample Use Case

```text
Use Case: TriggerSnackbar(message)
- Input: Message to show
- Output: Emits effect to show snackbar
```

---

## 📡 API Models & Sample Request/Response JSON

While this feature isn't API-dependent, components like Dialogs or Lists can showcase simulated data.

### Example: Fake List Item JSON

```json
[
  {
    "id": "1",
    "title": "Outlined Button",
    "description": "Demonstrates a button with outlined style."
  },
  {
    "id": "2",
    "title": "Modal Bottom Sheet",
    "description": "Shows how to use modal sheet for actions."
  }
]
```

---

## 🧭 Navigation Graph Flows

Navigation is handled using Jetpack Navigation + Hilt + Compose.

### Navigation Graph

```kotlin
sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Buttons: Screen("buttons")
    object Cards: Screen("cards")
    object Dialogs: Screen("dialogs")
    ...
}
```

### Navigation Host Flow

```
NavHost(startDestination = Screen.Home.route) {
    composable("home") { HomeScreen(...) }
    composable("buttons") { ButtonsScreen(...) }
    composable("cards") { CardsScreen(...) }
    ...
}
```

---

## 🎞️ Animations and Transitions

### Transition Guidelines:

- Entry/exit transitions: Fade or slide
- Component feedback: Ripple + elevation animation
- Snackbar: Slide from bottom
- BottomSheet: Slide-up w/ dim overlay
- Cards: Elevation on click
- Buttons: Elevation + color transition on press

---

## ⚠️ Error Handling & Edge Cases

While the design doesn’t involve backend integration, the UI should simulate failure states:

- Simulate errors in button state (e.g., no network response)
- Snackbar showing error messages
- Form validation in input/dialog components
- Missing/wrong component data showing fallback UI

### UI Patterns for Error:

- Dialog with retry
- Snackbar with action
- Placeholder card/message

---

## 📁 Folder Structure Recommendation

```
/src/main/java/com/example/m3gallery/
│
├── core/                         # App-wide base classes/utilities
│
├── data/
│   └── repository/
│       └── M3ComponentsRepositoryImpl.kt
│
├── domain/
│   └── models/
│   └── usecase/
│       └── TriggerSnackbarUseCase.kt
│
├── presentation/
│   ├── navigation/
│   │   └── NavGraph.kt
│   ├── components/
│   │   └── M3Button.kt
│   ├── screens/
│   │   ├── home/
│   │   ├── buttons/
│   │   ├── cards/
│   │   └── dialogs/
│   └── viewmodels/
│       └── ButtonsViewModel.kt
│
├── ui/theme/
│   └── Color.kt, Theme.kt, Typography.kt
```

---

## 🛠️ Steps to Implement

1. **Project Setup**
   - Enable Jetpack Compose
   - Add Material3 dependencies
   - Setup Navigation, Hilt, and StateFlow support

2. **Create Navigation Graph**
   - Create sealed class for routes
   - Implement NavHost and composable destinations

3. **Scaffold UI Structure**
   - Implement TopAppBar, BottomNav, Drawer
   - Navigation-driven Scaffold layout

4. **Component Screen Templates**
   - Create per-component screen: Buttons, Dialogs, Cards...
   - Each screen has ViewModel + State

5. **Develop M3 Component Demos**
   - Use Compose Material3 components
   - Show loading, disabled, error states
   - Match Figma styles closely

6. **Integrate Animations**
   - Use M3 Compose animation APIs

7. **Error State Simulations**
   - Simulate error rendering via ViewModel state

8. **Testing**
   - Add unit tests for ViewModel state
   - Optional: UI tests with Compose Testing APIs

9. **Polish and Finalize**
   - Match typography, colors, padding to M3 defaults
   - Accessibility evaluations

---

## 📎 Summary

This TDD provides a modular blueprint to develop an M3-compliant showcase demo app using Jetpack Compose, demonstrating Material 3 component behavior and Clean Architecture principles. Each component is encapsulated within its own screen, adheres to state handling via ViewModel, and can be extended to real-world use cases or backend integration.

---

📘 Reference:  
- Figma File: Material 3 Design Kit (Community)
- https://m3.material.io  
- https://developer.android.com/jetpack/compose  
- https://github.com/material-components/material-components-android