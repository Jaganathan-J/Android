# Assumptions & Technical Decisions
## Application: FitLife Finance

### 1. Product naming
While the prompt specified "App Name: FitLife", the wireframes were labeled "Finance Manager". I have reconciled this by naming the product **"FitLife Finance"**, positioning it as a "Financial Fitness" tool. All documentation assumes a Finance domain (Transactions, Currency, Budgets) rather than a Physical Fitness domain.

### 2. Tech Stack Rationale
- **Compose over XML:** XML is legacy. Starting a project in 2025 requires Compose for developer productivity and animation capabilities suitable for the Charting requirements.
- **Single Activity Architecture:** Standard best practice for memory management and transition animations.
- **Material 3:** The wireframes show rounded corners (Cards), clean typography, and flat surfaces, which align perfectly with M3 guidelines.

### 3. Third-Party Libraries
- **Vico/MPAndroidChart:** Writing charts from scratch is high-effort/low-reward. We assume usage of a library to render the Bar and Donut charts.
- **Room:** We assume the app is "Offline-First". Data is stored locally. This decision is based on the lack of explicit "Syncing" UI markers in the wireframe.

### 4. Wireframe Interpretations
- **"Coal" vs. "Goal":** The wireframe analysis text mentions "Coal". I have assumed this is a typo for **"Goal"** in the Budget Planner context.
- **"LOKOM":** The analysis mentions a toggle "LOKOM". I have interpreted this as a placeholder or specific View Toggle (e.g., "Local") and generalized it in the specs to a **Display Toggle**.
- **Navigation:** I assumed a Bottom Navigation bar is the best implementation for accessing Dashboard, Transactions, and Settings, even if the wireframe wasn't explicit on every screen, as it is standard android UX.

### 5. Analytics Strategy
- **Firebase Analytics:** Selected for free tier and Google integration.
- **Events:** We will track `transaction_added`, `goal_set`, `screen_view` to validate the KPIs defined in the PRD.