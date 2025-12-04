# Product Requirements Document (PRD): AutoFlow

## 1. Executive Summary
**AutoFlow** is a native Android application designed to democratize workflow automation for mobile users. In a landscape dominated by complex web-based integration platforms (like Zapier or Make), AutoFlow provides a simplified, mobile-first interface allowing users to connect their favorite services (Slack, Google Drive, Email, etc.) through linear "Trigger-Action" workflows.

The app focuses on the "Creator" persona—individuals who need to configure automations on the go without accessing a desktop. The core value proposition is speed and UX simplicity: a dark-themed, step-by-step wizard that guides users from naming a bot to deploying it in under 60 seconds.

## 2. Problem Statement
Currently, creating automation workflows requires desktop web dashboards that are non-responsive and overwhelming. Users often get ideas for automation while away from their desks (e.g., "I want to save this photo to Drive automatically"), but friction prevents immediate action. 

**Core Pain Points:**
1. **Complexity:** Existing tools expose too many API parameters to casual users.
2. **Mobility:** Lack of native mobile experiences for workflow configuration.
3. **Visibility:** Hard to track run history and success/failure states on mobile.

## 3. User Personas

### Persona A: The Busy Professional (Alex)
*   **Demographics:** 25-40, Project Manager/Marketer.
*   **Goal:** Automate repetitive notifications (e.g., "Email to Slack").
*   **Tech Literacy:** Moderate. Knows what they want but can't code.
*   **Pain Point:** Misses emails while in meetings; needs Slack alerts.

### Persona B: The Power User (Sam)
*   **Demographics:** 20-35, Developer/DevOps.
*   **Goal:** Trigger server scripts from a phone button.
*   **Tech Literacy:** High.
*   **Pain Point:** Wants to check automation logs ("Sance"/Success rates) while commuting.

## 4. Features & Prioritization (MoSCoW)

### Must Have (P0)
*   **User Authentication:** Secure Log In/Sign Up (Screens 1, 2).
*   **Workflow Wizard:** 4-step creation process (Name -> Trigger -> Action -> Review) (Screens 3-8).
*   **Service Integration:** Select Triggers/Actions from pre-defined lists (Screens 4-7).
*   **Persistence:** Save and activate workflows.
*   **Execution History:** View logs of past runs (Screen 9).

### Should Have (P1)
*   **User Profile:** Manage account settings (Screen 10).
*   **Toggle Automation:** Enable/Disable workflows directly from the list.
*   **Push Notifications:** Alert user on workflow failure.

### Could Have (P2)
*   **Multi-step Actions:** (Trigger -> Action A -> Action B).
*   **Visual Logic:** flowchart view (MVP shows linear list).

### Won't Have (MVP)
*   **Billing/Subscription Management** (Assumed free or web-managed).
*   **Custom API/Webhook Builder** (Pre-built integrations only).

## 5. Success Metrics (KPIs)
1.  **Time-to-Value:** Average time from Login to first Saved Automation < 2 minutes.
2.  **Completion Rate:** % of users who start the "Create Automation" flow and reach "Save".
3.  **Retention:** % of users returning to the "History" tab weekly.

## 6. Rollout Plan
*   **Phase 1 (Alpha):** Internal testing of UI flows and Mock APIs.
*   **Phase 2 (Beta):** Integration with real backend endpoints for Top 5 services (Google, Slack, Email).
*   **Phase 3 (Public):** Play Store launch with full Material 3 Dark theme support.