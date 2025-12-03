# Product Requirements Document (PRD)
## Project: Automation Overflow
**Version:** 1.0.0 | **Date:** October 26, 2025
**Status:** Approved for Development

### 1. Executive Summary

**Automation Overflow** is a native Android utility application designed to democratize workflow automation for mobile users. Similar to IFTTT or Zapier, it allows users to connect disparate services (Triggers) to outcomes (Actions) through a "No-Code" interface. The app focuses on a linear, user-friendly creation wizard that guides users through naming an automation, selecting a trigger event (e.g., "New Email"), and defining a resulting action (e.g., "Save to Drive").

The primary problem this solves is the fragmentation of mobile productivity tools. Users currently switch between apps to perform repetitive tasks. Automation Overflow serves as the central command center to link these apps seamlessly.

### 2. User Personas

**Primary Persona: Alex (The Tech-Forward Professional)**
*   **Demographics:** 28-40 years old, uses 10+ productivity apps daily.
*   **Pain Points:** Wastes time manually saving attachments, copying messages between Slack/Teams, and managing notifications.
*   **Goals:** Automate repetitive syncing tasks to save 1-2 hours per week.
*   **Behavior:** Uses dark mode exclusively; expects immediate feedback on system status.

**Secondary Persona: Jamie (The Smart Home Believer)**
*   **Demographics:** 25-35 years old.
*   **Pain Points:** Smart devices don't talk to each other natively.
*   **Goals:** Trigger lights/music based on location or calendar events.

### 3. Functional Requirements (MoSCoW)

#### Must Have (P0)
*   **Authentication:** Secure Login/Sign-up flow (Screens 1, 2) with token management.
*   **Workflow Creation Wizard:** A multi-step linear process to define Name, Trigger, and Action (Screens 3, 4, 5, 8).
*   **Trigger/Action Catalog:** Browse available integrations via List and Grid views (Screens 4, 5, 6, 7).
*   **Dashboard/Execution History:** View logs of past automation runs with success/failure status (Screen 9).
*   **Profile Management:** User settings, dark mode preferences (implied), and logout (Screen 10).

#### Should Have (P1)
*   **Toggle Automation:** Ability to pause/resume automations from the History or Detail view (Screen 9 toggles).
*   **Push Notifications:** Alerts when an automation fails.
*   **OAuth Integration:** Real Oauth2 linkage for 3rd party services (Slack, Google Drive).

#### Could Have (P2)
*   **Multi-Step Actions:** One trigger flows into multiple sequential actions.
*   **Complex Logic:** "Filter" steps (e.g., Only run if email subject contains "Urgent").

#### Won't Have (MVP)
*   User-defined custom API endpoints (Webhooks).
*   Tablet-specific layouts (Phone portrait only for MVP).

### 4. Success Metrics & KPIs
*   **Activation Rate:** % of new users who create at least one automation within 24 hours.
*   **Retention:** % of users with at least one *active* automation after 30 days.
*   **Error Rate:** % of execution failures (aiming for < 1%).

### 5. Rollout Plan
*   **Phase 1 (Alpha):** Internal testing, stubbed backend, UI validation.
*   **Phase 2 (Beta):** 500 users, functional backend with mock 3rd party APIs.
*   **Phase 3 (Production):** Full Play Store release, real integration with Gmail/Slack/Drive APIs.

### 6. Screen References (Traceability)
*   **REQ-AUTH-01:** Login Logic -> Maps to Screen 2.
*   **REQ-WIZ-01:** Naming Flow -> Maps to Screen 3.
*   **REQ-WIZ-02:** Select Trigger -> Maps to Screen 4 & 6.
*   **REQ-WIZ-03:** Select Action -> Maps to Screen 5 & 7.
*   **REQ-HIST-01:** Activity Logs -> Maps to Screen 9.
*   **REQ-PROF-01:** User Settings -> Maps to Screen 10.
