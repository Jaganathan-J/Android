# Technical Design Document (TDD)
Material 3 Contacts Showcase App (Clean Architecture, Jetpack Compose)

Version: 1.0  
Owner: Android Platform Team  
Audience: Senior Android Engineers, UX Designers, QA  
Figma Source: Material 3 Design Kit (Community)

---

## 1. Overview

### Purpose
Implement a reference Contacts Showcase app that demonstrates comprehensive usage of Material 3 components aligned with the Figma Material 3 Design Kit. The app highlights best practices in Clean Architecture, state-driven UI with Jetpack Compose, and Material motion.

### Business/UX Goals
- Provide a polished, accessible Material 3 implementation.
- Showcase core UI patterns: lists, search, chips, dialogs, bottom sheets, tabs, text fields, pickers, menus, snackbars, tooltips, carousels.
- Enable CRUD for contacts with avatars, badges, and details (birthday, labels, notes).
- Demonstrate offline-first behaviors, error handling, and graceful empty/loading states.

### Key Screens/Features
- Home (Contacts List + Search + Filters)
- Contact Details (Tabs: Info, Activity)
- Create/Edit Contact (Form with validation)
- Settings (App preferences)
- Global components: Dialogs, Snackbars, Tooltips, Bottom Sheets
- Navigation with Material transitions

---

## 2. Assumptions & Tech Stack

- Language: Kotlin
- UI: Jetpack Compose, Material 3
- Architecture: MVVM + Clean Architecture (Presentation, Domain, Data)
- Navigation: Jetpack Navigation (Compose)
- DI: Hilt
- Data: Retrofit + OkHttp (REST), Room (cache)
- Serialization: Kotlinx Serialization or Moshi (choose one per project standards)
- Coroutines + Flow
- Image Loading: Coil
- Build: Gradle (KTS), AndroidX
- Min SDK: 24+
- Theming: Dynamic color support (Android 12+), fallback palettes for prior versions
- Accessibility: TalkBack, large text, high contrast, touch targets, content descriptions

---

## 3. User Flow & Navigation

### High-level Flow
- App Launch → Home
- Home → Search (Top App Bar search mode)
- Home → Contact Details (on item tap)
- Contact Details → Edit Contact (FAB or Menu action)
- Home → Create Contact (FAB)
- Home → Filters Bottom Sheet (chips advanced)
- Overflow Menu → Settings

### Back/Up Behavior
- System back returns to previous screen/state (search mode -> exit search; edit -> confirm/discard dialog -> back to details/home).
- Up navigation mirrors back stack when applicable.

### Deep Links
- app://contacts/{contactId} → Contact Details
- app://contacts/create → Create Contact

---

## 4. UI Components & Layout Description

### 4.1 Home (Contacts List)
- Scaffold: 
  - Top App Bar: Medium/Center aligned app bar per M3 with:
    - Title: “Contacts”
    - Search affordance: Search icon enters Search mode (animated).
    - Overflow menu (three dots) → items: Settings, Help.
  - Bottom App Bar: Optional context actions hidden by default. FAB for “Create”.
- Content:
  - Search Field (in app bar when active): M3 Search with leading icon and clear action.
  - Filter Chips Row: Choice/Filter chips for categories (All, Favorites, Work, Family). Scrollable horizontally.
  - Carousel: “Favorites” horizontally scrollable carousel with large cards/avatars, showing name + status badge (badge color coding).
  - List: LazyColumn of contact items:
    - Leading: Avatar (image or initials), Badge (online/offline/verified)
    - Title: Name
    - Subtitle: Company or phone/email preview
    - Trailing: Overflow menu (per-item) with actions (Call, Message, Favorite, Delete).
  - Empty State: Illustrative icon, title, and CTA to create contact when list empty.
- Feedback:
  - Snackbar for undo operations (e.g., Delete).
  - Loading & progress indicators (pull-to-refresh and initial load).
- Tooltips:
  - Optional on first FAB exposure: “Create a new contact”.

### 4.2 Contact Details
- Top App Bar: Large/Medium with back/up, actions (Edit, Favorite, More).
- Header: 
  - Large Avatar with fallback initials.
  - Name, label chips (Work/Family), badges (favorite/verified).
- Tabs:
  - Info: Phone numbers, emails, birthday (date), address, notes. Lists with dividers.
  - Activity: Recent interactions (simulated list with date/time chips).
- Actions:
  - Primary: Call, Message, Email quick action chips/buttons.
- Bottom Sheet:
  - Additional actions (Share, Block, Delete) via modal bottom sheet from “More”.

### 4.3 Create/Edit Contact (Form)
- Top App Bar: Title (“Create contact” / “Edit contact”), Close/Back, Save action.
- Form fields (Text fields with supporting text and validation):
  - First Name, Last Name (required)
  - Company
  - Phone (formatted)
  - Email (validated)
  - Birthday (Date picker)
  - Label (Radio group: Personal, Work, Family; or Segmented buttons)
  - Favorite (Switch)
  - Notes (Multi-line)
  - Avatar: Image picker (optional)
- Supporting UI:
  - Helper/error messages inline.
  - Snackbars for form submission outcomes.
  - Confirmation dialog on unsaved changes when navigating away.

### 4.4 Settings
- Top App Bar: Small, with up navigation.
- Content:
  - Theme Mode: Radio list (System, Light, Dark)
  - Dynamic Color: Switch (if supported)
  - Text size: Slider (preview)
  - Notifications: Switches (General, Activity)
- Dialogs:
  - Confirm resets, etc.

### 4.5 Global Components Mapping (Figma → App Usage)
- App bars: Small/Medium/Large used across screens.
- Badges: Favorites/Status.
- Buttons: FAB, Filled, Tonal, Text; primary actions.
- Cards/Carousel: Favorite contacts.
- Checkboxes/Radio/Switch: Settings/Labels.
- Chips: Filter chips, Assist chips (actions), Suggestion chips (search).
- Date & time pickers: Birthday.
- Dialogs: Confirm unsaved changes, delete, error.
- Dividers: Lists.
- Lists: Contacts and details.
- Loading & progress: Indeterminate and pull-to-refresh.
- Menus: Overflow menus global and per-item.
- Navigation: Top app bar, tabs, bottom sheets for overflow.
- Radio button: Label selection.
- Search: App bar search mode with suggestions.
- Sheets: Modal bottom sheet for more actions/filter advanced.
- Sliders: Text size preference.
- Snackbar: Undo delete, saved success.
- Switch: Favorite toggle, settings toggles.
- Tabs: Details sub-sections.
- Text fields: Form entries.
- Toolbars/Tooltips: Hints on FAB on first run.

---

## 5. State Management (ViewModel Responsibilities)

General pattern per screen ViewModel:
- Immutable UI state data class (UiState) exposed as StateFlow.
- One-off side-effects (navigation, snackbars, dialogs) via SharedFlow or channels.
- UI intents handled as functions that mutate state using reducers.
- Business operations invoked via Use Cases returning Flow<Result<T>>.

Examples of responsibilities:

### HomeViewModel
- Load contacts (with cached-first strategy).
- Manage search query and suggestion results (debounced).
- Manage filter chips selection and filtered list.
- Handle favorite toggles, delete with undo (optimistic update).
- Expose loading/error/empty states.
- Trigger navigation to details/create.

### ContactDetailsViewModel
- Load contact details by id; handle tabs internal state.
- Handle actions: favorite, call/message/email intents (emits side-effect).
- Open bottom sheet actions; delete with confirm.
- Navigate to edit.

### EditContactViewModel
- Initialize with existing data (if editing).
- Form state: fields, validity, errors.
- Save contact (create or update) with progress state.
- Image selection state and persistence.
- Unsaved changes detection; confirm discard.

### SettingsViewModel
- Load and persist preferences (theme, dynamic color, text size, notifications).
- Apply changes via app-level state (single source of truth).

---

## 6. Domain, Repository, and Use Cases

### Domain Entities
- Contact:
  - id: String
  - firstName: String
  - lastName: String
  - company: String?
  - phones: List<Phone>
  - emails: List<Email>
  - birthday: String? (ISO-8601 date)
  - label: ContactLabel (Personal, Work, Family)
  - favorite: Boolean
  - avatarUrl: String?
  - verified: Boolean
  - status: Status (Online, Offline) — optional computed for demo
  - notes: String?
  - updatedAt: String (ISO-8601)
- Phone: type, number
- Email: type, address

- Preferences:
  - themeMode: ThemeMode (System, Light, Dark)
  - dynamicColor: Boolean
  - textScale: Float (0.85–1.3)
  - notifications: NotificationsPref

### Repository Interfaces
- ContactsRepository
  - getContacts(query, filter, page)
  - getContact(id)
  - createContact(contact)
  - updateContact(contact)
  - deleteContact(id)
  - toggleFavorite(id, favorite)
  - observeContactsCache()
- PreferencesRepository
  - observePreferences()
  - updatePreferences(prefs)

### Use Cases (interactors)
- GetContactsUseCase
- SearchContactsUseCase (debounced)
- GetContactDetailsUseCase
- CreateContactUseCase
- UpdateContactUseCase
- DeleteContactUseCase
- ToggleFavoriteUseCase
- GetPreferencesUseCase
- UpdatePreferencesUseCase

Behavioral Notes:
- Offline-first: queries return cached data immediately, refresh from remote, merge with diff.
- Optimistic updates for favorite toggle and delete; rollback on failure with snackbar.

---

## 7. API Models & Sample Request/Response JSON

Base URL: https://api.example.com

Authentication: (Assume none for demo; add token header if needed.)

### List Contacts
GET /v1/contacts?query={q}&filter={label|favorites|all}&page={n}&pageSize={m}

Response 200:
```
{
  "data": [
    {
      "id": "c_123",
      "first_name": "Ana",
      "last_name": "Gomez",
      "company": "Acme Inc.",
      "phones": [{"type": "mobile", "number": "+1 555 111 2222"}],
      "emails": [{"type": "work", "address": "ana.gomez@acme.com"}],
      "birthday": "1990-08-17",
      "label": "work",
      "favorite": true,
      "avatar_url": "https://.../avatars/c_123.png",
      "verified": true,
      "notes": "Key account",
      "updated_at": "2025-10-01T14:22:00Z"
    }
  ],
  "pagination": {"page": 1, "page_size": 20, "total_pages": 5, "total_items": 100}
}
```

### Get Contact
GET /v1/contacts/{id}

Response 200:
```
{
  "id": "c_123",
  "first_name": "Ana",
  "last_name": "Gomez",
  "company": "Acme Inc.",
  "phones": [{"type": "mobile", "number": "+1 555 111 2222"}],
  "emails": [{"type": "work", "address": "ana.gomez@acme.com"}],
  "birthday": "1990-08-17",
  "label": "work",
  "favorite": true,
  "avatar_url": "https://.../avatars/c_123.png",
  "verified": true,
  "notes": "Key account",
  "updated_at": "2025-10-01T14:22:00Z"
}
```

### Create Contact
POST /v1/contacts  
Body:
```
{
  "first_name": "Lena",
  "last_name": "Park",
  "company": "Globex",
  "phones": [{"type": "mobile", "number": "+1 555 333 4444"}],
  "emails": [{"type": "personal", "address": "lena@example.com"}],
  "birthday": "1992-04-12",
  "label": "personal",
  "favorite": false,
  "avatar_url": null,
  "notes": "Met at conference"
}
```
Response 201:
```
{
  "id": "c_789",
  "updated_at": "2025-10-02T09:12:00Z"
}
```

### Update Contact
PUT /v1/contacts/{id}  
Body: same as create, full or patchable fields.

Response 200:
```
{"updated_at":"2025-10-03T10:00:00Z"}
```

### Delete Contact
DELETE /v1/contacts/{id}
Response 204 (no content)

### Toggle Favorite
PATCH /v1/contacts/{id}/favorite  
Body:
```
{"favorite": true}
```
Response 200:
```
{"favorite": true, "updated_at":"2025-10-03T11:00:00Z"}
```

### Error Format
Response 4xx/5xx:
```
{
  "error": {
    "code": "invalid_input",
    "message": "First name is required",
    "details": {"field": "first_name"}
  }
}
```

---

## 8. Navigation Graph Flows

Graph: root -> home, details, edit, settings.

- home
  - route: home
  - actions: toDetails(contactId), toCreate(), toSettings()
- details
  - route: details/{contactId}
  - args: contactId (String)
  - actions: toEdit(contactId), up()
- edit
  - route: edit?contactId={optional}
  - args: nullable contactId (if null → create)
  - actions: upWithConfirmIfDirty(), onSavedNavigateBackToDetailsOrHome
- settings
  - route: settings
  - actions: up()

Modal flows:
- filters_sheet (modal bottom sheet from home)
- actions_sheet (details “More”)

Deep links:
- app://contacts/{contactId} → details
- app://contacts/create → edit (no id)

Start destination: home

---

## 9. Animations & Transitions

- Screen transitions:
  - Home → Details: Shared axis (X) forward; Details → Home reverse.
  - Details → Edit: Shared axis (Y) forward; reverse on back.
  - Home → Settings: Fade through.
- App bar collapse/expand:
  - Medium/Large app bar with scroll behavior on lists.
- List item interactions:
  - Ripple on tap; state layer per M3.
  - Favorite toggle: spring scale on icon change.
  - Delete: item slide-out + collapse; undo restores with expand-in.
- Chips:
  - Selection with color/tonal change and subtle scale.
- Search mode:
  - Crossfade app bar title ↔ search field; shared axis for content filter results.
- Bottom sheets:
  - Standard M3 modal sheet entrance from bottom.
- Snackbars:
  - Standard slide/fade from bottom.
- Carousels:
  - Snap scrolling with subtle parallax on avatar.

Motion duration/easing:
- Use Material emphasized/de-emphasized curves.
- Motion durations 200–400ms per Material guidance.

---

## 10. Error Handling & Edge Cases

General:
- Network errors: show non-blocking snackbar with retry; critical loads show error state with retry button.
- Offline: serve from cache; indicate “Offline” with banner chip; disable destructive network actions.
- API validation errors: map to field-level errors in forms; show combined snackbar summary.
- Timeouts: fallback to cache with banner.
- Unauthorized (if added): route to sign-in or show restricted messaging.

Home:
- Empty states: no contacts, no search results, no favorites → show illustration + guidance.
- Long names: ellipsize properly, test RTL and large font.
- Missing avatars: fallback to initials with deterministic color.
- Large lists: paginate/hide FAB on scroll.

Details:
- Missing fields (no email/phone): hide sections.
- Actions that require apps (call/email): gracefully handle missing intent handler.

Edit:
- Validation: required names, valid email format, phone normalization.
- Duplicates: show warning (non-blocking).
- Unsaved changes: confirm navigation away.
- Image picker failures: show snackbar; keep previous avatar.

Settings:
- Dynamic color support check; disable toggle on unsupported OS.
- Slider with min/max and live preview.

Accessibility:
- Content descriptions for icons/avatars.
- Minimum 48dp touch target.
- Contrast ratios per Material.
- TalkBack announcements for snackbars and navigation.
- Keyboard navigation support on ChromeOS.

---

## 11. Data & Caching Strategy

- Room database tables:
  - contacts, phones, emails, metadata (pagination), sync_state
- Cache policy:
  - Stale-while-revalidate: return cached list immediately; refresh network in background.
  - ETags/If-Modified-Since if supported; else updated_at as last sync marker.
- Conflict resolution:
  - Last-write-wins based on updated_at.
- Paging:
  - Simple page-based or token-based; keep consistent with API.

---

## 12. Folder Structure Recommendations

Modularization (recommended multi-module):
- app
  - hosts navigation, DI entry points
- core:common
  - utils, error mapping, dispatchers, analytics, logging
- core:designsystem
  - theme, typography, colors, shapes, components wrappers (M3 atoms/molecules), icons
- core:ui
  - common Compose elements (snackbar host, empty states, loading, tooltips)
- domain
  - entities, repositories (interfaces), use cases
- data:contacts
  - remote (API DTOs, Retrofit), local (Room DAOs), repository implementation, mappers
- data:preferences
  - datastore/room for preferences, repository implementation
- feature:home
- feature:details
- feature:edit
- feature:settings

Package structure per module:
- presentation (ui, viewmodel, navigation, state)
- domain (usecases, models)
- data (repository, remote, local, mappers)
- di

Resource organization:
- images/avatars, strings (i18n), themes, dimens, icons (vector)

---

## 13. Navigation Graph Specifications

Routes and arguments:
- home
- details/{contactId}
  - arguments: contactId: String (required)
- edit?contactId={contactId}
  - arguments: contactId: String? (nullable)
- settings

Modal sheets defined as bottom sheet destinations or in-screen composables:
- filters_sheet (invoked via state in home)
- actions_sheet (invoked via state in details)

Back stack behavior:
- After creating a contact from Home, navigate to Details then back returns to Home.
- After editing from Details, pop back to Details with updated data (singleTop for details route to avoid duplicates).

---

## 14. Theming & Styles

- Material 3 color scheme using dynamic color on Android 12+; static palettes for lower versions (Light/Dark).
- Shapes: medium roundness (per Figma kit defaults).
- Typography: M3 defaults with scale adjustable via Settings.
- Elevation: tonal elevation; low elevation surfaces for lists, medium for cards.
- Iconography: Filled/Outlined sets consistent with Figma.

---

## 15. Analytics (Optional)

Events:
- contacts_viewed, contact_selected, search_performed, contact_created, contact_updated, contact_deleted, favorite_toggled, settings_changed.
- Include error events: network_error, validation_error.

Respect user privacy; allow opt-out in Settings.

---

## 16. Security & Privacy

- Avoid storing sensitive data.
- Secure network with HTTPS.
- Input validation and sanitization client-side; server-side authoritative validation.
- Image URLs loaded over HTTPS; cache headers respected.

---

## 17. QA & Testing Strategy

- Unit tests: ViewModels, Use Cases, Mappers.
- Integration tests: Repository with fake remote/local.
- UI tests: Compose testing for states (loading/empty/error), navigation, form validation, accessibility.
- Snapshot testing: Theming variants (Light/Dark/Dynamic), font scales, RTL.

---

## 18. Steps to Implement

Phase 0: Project Setup
- Configure modules and Gradle; add dependencies (Compose, Material3, Navigation, Hilt, Retrofit, Room, Coil).
- Set up Hilt application class and base DI.

Phase 1: Design System
- Implement theme, typography, shapes, color scheme (dynamic + fallback).
- Build common components: app bars, list items, chips, empty/loading, snackbars host.

Phase 2: Data Layer
- Define DTOs and Retrofit service interfaces for contacts.
- Implement Room entities/DAOs and database.
- Create mappers DTO↔Entity↔Domain.
- Implement ContactsRepository and PreferencesRepository with offline-first strategy.
- Error mapping to domain layer.

Phase 3: Domain Layer
- Define domain models and repository interfaces.
- Implement use cases.
- Add unit tests for use cases and repositories.

Phase 4: Feature: Home
- Define UiState, intents, ViewModel logic (load, search, filter, favorite, delete/undo).
- Compose UI: Scaffold, app bar with search mode, chips, carousel, list, empty/loading, snackbar host.
- Integrate bottom sheet filters.
- Navigation actions to Details, Edit, Settings.
- Tests for states and interactions.

Phase 5: Feature: Details
- ViewModel: load by id, tab management, actions (favorite, delete, share).
- UI: header, tabs (Info/Activity), bottom sheet actions.
- Motion transitions.
- Tests.

Phase 6: Feature: Edit
- ViewModel: form state, validation, save, unsaved changes.
- UI: fields, date picker, radio buttons, switch, image picker, dialogs.
- Success flow back to Details/Home.
- Tests.

Phase 7: Feature: Settings
- Preferences datastore, ViewModel binding.
- UI: theme mode, dynamic color, text size slider, notification toggles.
- Apply theme changes at app level.
- Tests.

Phase 8: Polish & Accessibility
- Animations per Material motion.
- Tooltips onboarding (FAB).
- Accessibility audits: TalkBack, large text, contrast.
- RTL validation.

Phase 9: Performance & Stability
- Lazy lists performance checks, image caching.
- StrictMode analysis.
- Offline scenarios validation.

Phase 10: Release Artifacts
- README with architecture.
- Changelog.
- Instrumentation test reports.

---

## 19. Acceptance Criteria

Home:
- Displays cached list instantly; refreshes from network.
- Search debounced; shows suggestions and results.
- Filters selectable via chips; persistent across sessions.
- Favorites carousel shows accurate data.

Details:
- Tabs switch stateful; actions work; bottom sheet actions displayed.

Edit:
- Form validation; Save enabled only when valid; unsaved changes confirmation.

Settings:
- Theme changes applied immediately; dynamic color toggles when supported.

Global:
- Material 3 styles; motion aligned; a11y compliant.
- Error/empty/loading states implemented for all data-driven views.

---

## 20. Open Questions

- Authentication required? If yes, add Auth module and protected endpoints.
- Pagination style: page vs. cursor (current examples assume page).
- Activity tab data source: simulated or real?
- Image upload support or URL-only?

Once clarified, update API and data/storage accordingly.

---

## 21. Mapping to Figma Pages (Traceability)

- App bars: Home, Details, Edit, Settings
- Badges: Favorite/Status/Verified on avatars and chips
- Buttons: FAB on Home, Save in Edit, action chips in Details
- Cards/Carousel: Favorites on Home
- Checkboxes/Radio/Switch: Labels and Settings
- Chips: Filters on Home, Labels in Details
- Date & time pickers: Birthday
- Dialogs: Confirm delete, unsaved changes, errors
- Dividers: Lists on Details
- Lists: Contacts and activity
- Loading & progress: Initial load, pull-to-refresh
- Menu: Overflow menus on Home and Details
- Navigation: Tabs, Top app bars, Sheets
- Radio button: Label selection
- Search: App bar search mode
- Sheets: Filters and More actions
- Sliders: Text size in Settings
- Snackbar: Undo, Saved
- Switch: Favorite and Settings
- Tabs: Details sections
- Text fields: All form inputs
- Toolbars/Tooltips: FAB tooltip on first run

---

End of Document.