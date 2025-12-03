# Technical Decisions & Assumptions

## 1. Assumptions based on Wireframes
*   **Wizard State**: The creation flow is shown as separate screens. I assume a shared `ViewModel` (scoped to a distinct Navigation Graph) or passing arguments between routes is required to hold the "Draft Workflow" state until the final Save.
*   **Trigger Configuration**: The wireframes show a list of triggers but no detailed configuration screen (e.g., "Which Email address?"). **Assumption**: For MVP, triggers use default parameters or the backend handles configuration via OAuth permissions (e.g., "Any email received").
*   **Dark Mode**: The design is exclusively dark. We will force `AppCompatDelegate.MODE_NIGHT_YES` or set the Compose theme to hardcoded dark colors regardless of system setting for consistent branding.

## 2. Technical Decisions
*   **Why specific Gradient Buttons?**: The UI relies heavily on the blue-purple gradient. We will implement a custom `GradientButton` composable in `:core:designsystem` rather than using standard Material 3 buttons, to ensure pixel-perfect fidelity.
*   **Why Room Database?**: "Execution History" implies logs. To support offline viewing and minimize API calls on every screen load, we will cache history logs in a local SQLite database using Room.
*   **Single Activity Architecture**: We will use a single `MainActivity` hosting a `NavHost`. This ensures smooth transitions and simpler lifecycle management compared to multi-activity apps.
*   **Coil for Images**: The Profile screen and Trigger icons need image loading. Coil is the only image loader built entirely for Kotlin Coroutines/Compose, making it the default choice over Glide/Picasso.