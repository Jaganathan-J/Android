# Dynamic Material Theme Sync — Technical Design Document (TDD)

Product: Android app  
Architecture: Clean Architecture (Domain / Data / Presentation)  
UI Toolkit: Jetpack Compose + Material 3  
DI: Hilt  
Persistence: DataStore Preferences  
Language: Kotlin  
Supported Android: 8.0 (API 26)+, with Dynamic Color on Android 12 (API 31)+

---

## 1) Overview

Goal  
Implement a Dynamic Material Theme Sync feature that automatically updates the app’s color palette, typography, shapes, elevation, and iconography based on a Material 3 theme JSON exported from Figma’s Material Theme Builder. The system persists user choices, supports light/dark, respects system dynamic color (Android 12+), and syncs typography fonts and Material Symbols.

Key Outcomes
- Fetch and parse theme tokens (colors, typography, shapes, elevation) from a Theme JSON exported from Figma.
- Build MaterialTheme at runtime (ColorScheme, Typography, Shapes) in Compose.
- Sync typography with Google Fonts and icons with Material Symbols styles.
- Persist selected theme and overrides in DataStore.
- Honor system dynamic color where available and user-configurable.
- DI-driven, testable, offline-friendly with graceful fallbacks.

Out of Scope
- In-app Figma authorization or direct Figma API integration; assumes a theme JSON is available remotely or bundled.
- Custom icon packs beyond Material Symbols.

---

## 2) User Flow & Navigation

Primary Flows
- First Launch
  - If user has no stored preferences: use system dynamic color (Android 12+) or default app theme. Schedule background sync from configured theme JSON endpoint.
- Manual Sync
  - From Theme Settings, user triggers “Sync from Figma Theme JSON” to refresh tokens. Preview updates, then Apply.
- Toggle Dynamic Color
  - On Android 12+, user toggles dynamic color on/off. When on, color roles derive from system palette; typography/shapes still apply from theme tokens unless overridden.
- Choose Theme Source
  - User selects source: System (dynamic), Remote Theme JSON, Local Theme JSON (bundled), or Manual override per token group.
- Typography Selection
  - User selects preferred font family from Theme JSON or from Google Fonts suggestion list in the theme. Preview live text styles; Apply.
- Icon Style Selection
  - User selects Material Symbols style: Filled / Outlined / Rounded. Optionally weight/grade settings if supported by icon font.
- Reset to Default
  - Reset to system defaults. Clear overrides and cached assets.

Navigation Map
- AppRoot (MainGraph)
  - Home
  - Settings
    - Theme Settings (feature root)
      - Theme Preview (embedded section or separate screen)
      - Typography Picker (screen)
      - Icon Style Picker (bottom sheet)
      - Theme Source Selector (dialog/sheet)
      - Sync Confirmation (dialog)
      - Error & Recovery (snackbar/dialog)

Back Behavior
- Standard back returns to previous screen. Pending changes are kept in ViewModel until applied.

Deep Links
- app://settings/theme opens Theme Settings.
- app://settings/theme/sync triggers a sync action and opens confirmation UI.

---

## 3) UI Components & Layout

A) Theme Settings Screen
- Top App Bar: Large or Center-aligned, title “Theme”
- Sections (Material 3 preference layout):
  - Theme Source
    - Selector Row: System Dynamic (Android 12+ only), Remote JSON, Local JSON
    - Status: “Synced 2 min ago”, “Out of date”, “Offline”
    - Action: Sync Now button
  - Color
    - Toggle: Use Dynamic Color (Android 12+) [switch]
    - When Dynamic Color off: chip group shows Primary, Secondary source (Remote/Local). Info row with last token version.
    - Preview: color roles chips (Primary/OnPrimary, Secondary, Tertiary, Surface, Background, Error)
  - Typography
    - Row: Current Family (e.g., “Roboto Flex”) → opens Typography Picker
    - Subtext: “From Theme JSON / Google Fonts”
    - Preview: Headline/Title/Body samples
  - Icons
    - Row: Material Symbols Style → opens Icon Style Picker (Filled/Outlined/Rounded)
    - Option sliders if supported: Weight, Grade, Optical size (optional configuration from theme)
    - Preview: Sample icons in various states
  - Shapes & Elevation
    - Shapes preset from theme: Small/Medium/Large corner radii display
    - Elevation tokens preview with surfaces
  - Persistence
    - Switch: “Apply on launch”
    - Button: Reset to Default
- Feedback
  - Snackbar for success/fail
  - Loading indicators while syncing/downloading fonts/icons
  - Error banners when JSON invalid/outdated

B) Typography Picker Screen
- Search bar for font family (optional)
- List of font families proposed by Theme JSON; “See more” loads via Google Fonts metadata
- Preview area with displayLarge/titleMedium/bodyMedium etc
- Actions: Cancel, Apply

C) Icon Style Picker (Bottom Sheet)
- Segmented control: Filled / Outlined / Rounded
- Optional sliders (if icon font axes supported): Weight, Grade, Optical size
- Preview grid: representative icons in enabled/disabled/selected states
- Actions: Cancel, Apply

D) Theme Source Selector (Dialog/Sheet)
- Radio options:
  - System Dynamic (Android 12+)
  - Remote JSON (URL)
  - Local JSON (bundled asset version x.y.z)
- Input field (if Remote): URL (validated)
- Actions: Cancel, Select

E) Theme Preview (Inline section or separate)
- Two mobile mock previews (Light/Dark)
- Apply button commits staged config
- Transition: crossfade between current and staged theme on preview only

Accessibility
- Contrast checks on primary backgrounds
- Minimum touch target 48dp
- TalkBack content descriptions for previews and status

---

## 4) State Management (ViewModel Responsibilities)

Primary ViewModel: ThemeViewModel
- Exposed UI State (single source of truth):
  - loading: Boolean
  - status: Idle | Syncing | Synced | Error(message)
  - themeSource: SystemDynamic | Remote(url) | Local(assetName)
  - supportsDynamicColor: Boolean
  - useDynamicColor: Boolean
  - effectiveColorScheme: Material3 ColorScheme snapshot (for preview)
  - effectiveTypography: Material3 Typography snapshot
  - effectiveShapes: Material3 Shapes snapshot
  - elevationTokens: Elevation levels
  - iconStyle: Filled | Outlined | Rounded
  - iconAxes: weight, grade, opticalSize (where applicable)
  - availableFonts: list of FontOption (name, source, variants availability)
  - availableIconStyles: enum list
  - lastSyncTime, themeVersion
  - pendingChanges: Boolean
- Inputs/Intents:
  - onSelectThemeSource(source)
  - onToggleDynamicColor(enabled)
  - onRequestSync()
  - onSelectTypography(fontFamily)
  - onSelectIconStyle(style)
  - onUpdateIconAxes(weight, grade, opsz)
  - onApplyChanges()
  - onResetDefaults()
- Logic:
  - Combine flows from:
    - DataStore preferences (user overrides)
    - ThemeRepository (current remote/local theme tokens)
    - DynamicColorProvider (Android 12+)
    - FontRepository (resolved Typeface metadata)
    - IconRepository (symbols style configuration)
  - Map tokens to Material3 objects
  - Debounce user changes for preview
  - Validate theme JSON schema/version compatibility
  - Cache last good configuration and fall back on error
  - Emit UI Events for snackbars/toasts

Secondary ViewModels (optional):
- TypographyPickerViewModel: lists/searches fonts, manages preview selection
- IconPickerViewModel: manages icon style/axes options

---

## 5) Repository + Use Case Structure

Repositories (Interfaces in Domain; implementations in Data)
- ThemeRepository
  - getThemeTokens(): Flow<ThemeTokens>
  - syncThemeTokens(source): Result<ThemeTokens>
  - getLocalThemeTokens(): ThemeTokens?
  - cacheThemeTokens(tokens)
  - getLastSyncInfo(): SyncInfo
- FontRepository
  - getPreferredFont(): Flow<FontConfig>
  - resolveFontFromTheme(tokens): Result<FontConfig>
  - fetchFontMetadata(family): Result<FontMetadata>
  - cacheFontFiles(metadata)
- IconRepository
  - getIconStyle(): Flow<IconStyleConfig>
  - setIconStyle(style, axes)
  - supportedStyles(): List<IconStyle>
- PreferencesRepository
  - observeThemePreferences(): Flow<ThemePreferences>
  - updateThemePreferences(prefs)
  - clear()
- DynamicColorProvider
  - isSupported(): Boolean
  - getLightScheme(): ColorScheme
  - getDarkScheme(): ColorScheme

Use Cases
- ObserveThemeUseCase: combines tokens + preferences + dynamic color → ThemeModel for UI
- SyncThemeUseCase: fetch/validate theme JSON from source; update repository cache; return status
- ApplyThemeUseCase: persist current staged selection to DataStore; update repositories if needed
- ToggleDynamicColorUseCase
- UpdateTypographyUseCase: set preferred font; trigger font resolution/download
- UpdateIconStyleUseCase: set symbol style and axes
- ResetThemeUseCase: clear preferences; restore defaults
- ValidateThemeJsonUseCase: schema/version compatibility checks
- BuildMaterialThemeUseCase: map ThemeTokens + overrides to ColorScheme, Typography, Shapes, Elevation

Data Sources
- RemoteThemeDataSource (HTTP)
- LocalThemeDataSource (assets/raw or file cache)
- PreferencesDataSource (DataStore)
- FontsDataSource (Google Fonts metadata endpoint or packaged list)
- IconsDataSource (Material Symbols style configuration—local constants or remote config)
- Cache (disk for theme JSON, fonts; memory LRU)

---

## 6) API Models & Sample JSON

Theme JSON (exported from Figma Material Theme Builder)

Sample Response (Remote or Local)
- GET https://example.com/themes/material3.json

Example JSON (truncated):
{
  "schemaVersion": "1.0",
  "themeVersion": "2025.02",
  "name": "Brand X",
  "colors": {
    "light": {
      "primary": "#6750A4",
      "onPrimary": "#FFFFFF",
      "primaryContainer": "#EADDFF",
      "onPrimaryContainer": "#21005D",
      "secondary": "#625B71",
      "onSecondary": "#FFFFFF",
      "background": "#FFFBFE",
      "onBackground": "#1C1B1F",
      "surface": "#FFFBFE",
      "onSurface": "#1C1B1F",
      "surfaceVariant": "#E7E0EC",
      "onSurfaceVariant": "#49454F",
      "tertiary": "#7D5260",
      "error": "#B3261E",
      "onError": "#FFFFFF",
      "outline": "#79747E",
      "inverseSurface": "#313033",
      "inverseOnSurface": "#F4EFF4"
    },
    "dark": {
      "primary": "#D0BCFF",
      "onPrimary": "#381E72",
      "primaryContainer": "#4F378B",
      "onPrimaryContainer": "#EADDFF",
      "secondary": "#CCC2DC",
      "onSecondary": "#332D41",
      "background": "#1C1B1F",
      "onBackground": "#E6E1E5",
      "surface": "#1C1B1F",
      "onSurface": "#E6E1E5",
      "surfaceVariant": "#49454F",
      "onSurfaceVariant": "#CAC4D0",
      "tertiary": "#EFB8C8",
      "error": "#F2B8B5",
      "onError": "#601410",
      "outline": "#938F99",
      "inverseSurface": "#E6E1E5",
      "inverseOnSurface": "#313033"
    }
  },
  "typography": {
    "fontFamily": "Roboto Flex",
    "fallback": ["Roboto", "Noto Sans"],
    "weightMapping": {
      "displayLarge": 400,
      "titleMedium": 500,
      "bodyMedium": 400
    },
    "sizeSp": {
      "displayLarge": 57,
      "headlineMedium": 28,
      "titleMedium": 16,
      "bodyMedium": 14,
      "labelLarge": 14
    },
    "lineHeightSp": {
      "displayLarge": 64,
      "headlineMedium": 36,
      "titleMedium": 24,
      "bodyMedium": 20,
      "labelLarge": 20
    },
    "letterSpacingEm": {
      "labelLarge": 0.1
    }
  },
  "shapes": {
    "cornerFamily": "rounded",
    "extraSmall": 4,
    "small": 8,
    "medium": 12,
    "large": 16,
    "extraLarge": 28
  },
  "elevation": {
    "level0": 0,
    "level1": 1,
    "level2": 3,
    "level3": 6,
    "level4": 8,
    "level5": 12
  },
  "icons": {
    "materialSymbolsStyle": "filled",
    "defaultWeight": 400,
    "defaultGrade": 0,
    "defaultOpticalSize": 24
  },
  "meta": {
    "generatedBy": "Figma Material Theme Builder",
    "updatedAt": "2025-02-10T11:00:00Z"
  }
}

Fonts Metadata (Google Fonts)
- GET https://www.googleapis.com/webfonts/v1/webfonts?key=YOUR_API_KEY
- Response (excerpt):
{
  "items": [
    {
      "family": "Roboto Flex",
      "variants": ["regular", "italic", "700"],
      "axes": ["wdth", "wght", "GRAD", "opsz"],
      "files": { "regular": "https://fonts.gstatic.com/s/robotoflex/v..." }
    }
  ]
}

Notes
- If the app avoids network font fetches, package critical fonts and switch to local fonts when unavailable.
- Material Symbols can be referenced via the Material Symbols variable font with style selection (filled/outlined/rounded).

---

## 7) Navigation Graph Flows

Graph: SettingsGraph
- settingsRoot
  - themeSettings (start of feature)
    - actions:
      - toTypographyPicker
      - toIconPickerSheet
      - toThemeSourceSelector
      - showSyncConfirmDialog
      - showErrorDialog
  - popUpTo: settingsRoot inclusive=false on return to settings

Transitions
- Standard Material motion for screen transitions (fade-through)
- Bottom sheet: standard modal bottom sheet enter/exit
- Dialogs: scale + fade

---

## 8) Animations & Transitions

- Theme Application
  - Crossfade the entire content area when applying a new theme to minimize jank.
  - Duration: 200–300ms; use a short ease-out.
- Color Preview Changes
  - Animate color chip backgrounds with spring or tween 200ms.
- Typography Preview
  - Fade between text styles on selection (150–200ms); avoid animating font metrics directly to prevent layout thrash.
- Icon Style Changes
  - Crossfade icon previews; if using variable icon font axes, animate numeric changes with a short tween and recomposition.
- Settings Navigation
  - Material fade-through between screens and content size animations for expanding sections.

---

## 9) Error Handling & Edge Cases

- Network Failures (theme JSON or fonts)
  - Fallback to last cached tokens; show snackbar “Using last saved theme”
  - Provide “Retry” action
- Invalid JSON / Schema Mismatch
  - Validate schemaVersion; if incompatible, ignore remote update and notify user
- Missing Tokens
  - Apply partial updates: only override provided tokens
  - Missing typography → fallback to default Typography
  - Missing shapes/elevation → fallback to defaults
- Dynamic Color Unavailable (pre-Android 12)
  - Hide dynamic color switch; auto-switch to theme tokens
- Font Download Timeout or Unsupported Axis
  - Use packaged fallback font; disable axis sliders
- Icon Style Unsupported
  - Revert to default Material Symbols style (Outlined)
- Corrupted Cache
  - Clear cache and reload; notify user
- DataStore Write Failure
  - Show error; keep staged changes in memory; allow retry
- Dark/Light Mode Switch
  - Ensure the correct role mappings exist; if dark tokens missing, derive via tonal palettes or fallback to light with adjusted contrast
- Performance and Jank
  - Avoid animating the entire UI frequently; batch state updates
- Accessibility Contrast
  - Detect insufficient contrast in custom tokens; warn user and optionally auto-adjust (non-blocking notice)

---

## 10) Folder Structure Recommendations

- app/
  - di/
    - Modules for repositories, use cases, DataStore, network, fonts, icons
  - presentation/
    - theme/
      - ThemeSettingsScreen
      - TypographyPickerScreen
      - IconStylePickerSheet
      - components/ (preview chips, tokens visuals, rows)
      - ThemeViewModel
      - mappers/ (ThemeModel → UI models)
  - domain/
    - model/
      - ThemeTokens, ColorTokens, TypographyTokens, ShapeTokens, ElevationTokens
      - ThemePreferences, FontConfig, IconStyleConfig, ThemeModel
    - repository/
      - ThemeRepository, FontRepository, IconRepository, PreferencesRepository, DynamicColorProvider
    - usecase/
      - ObserveThemeUseCase, SyncThemeUseCase, ApplyThemeUseCase, ToggleDynamicColorUseCase, UpdateTypographyUseCase, UpdateIconStyleUseCase, ResetThemeUseCase, ValidateThemeJsonUseCase, BuildMaterialThemeUseCase
  - data/
    - repository/
      - ThemeRepositoryImpl, FontRepositoryImpl, IconRepositoryImpl, PreferencesRepositoryImpl
    - source/
      - remote/ RemoteThemeDataSource (http client abstraction)
      - local/ LocalThemeDataSource (assets/cache), FontsDataSource, IconsDataSource
      - preferences/ DataStore source
    - mapper/
      - Json → ThemeTokens, Tokens → Material3 objects (domain mapping)
    - cache/
      - file handling for JSON and fonts
  - core/
    - network/ HttpClient, interceptors
    - util/ Result wrappers, validators, color utils
    - platform/ DynamicColorProviderImpl
  - resources/ (if any non-runtime assets)
  - build-logic/ (optional configuration)

---

## 11) Mapping & Construction Details

ColorScheme Resolution (priority)
1) If useDynamicColor true and supported: use dynamicColorScheme(context) light/dark.
2) Else if ThemeTokens.colors for current mode available: use them directly.
3) Else: derive dark from light using tonal algorithms (if available) or fallback defaults.

Typography Resolution
- Use ThemeTokens.typography mapping to Material3 text styles.
- Resolve font family:
  - Prefer theme font if available locally or fetched; else fallback chain (ThemeTokens.fallback → system default).
- Apply weight/size/lineHeight from tokens where provided; else Material defaults.

Shapes
- Map corner radii from tokens to MaterialShapes (extraSmall..extraLarge). Fill missing ones with nearest values or Material defaults.

Elevation
- Map levels (0..5) to Elevation tokens used in surfaces and components. If not provided, use Material defaults.

Icons
- Store icon style enum; for Material Symbols variable font, store axes (weight, grade, optical size) if used for vector rendering through font. If using static icon sets via dependency, map style to corresponding set.

Persistence
- DataStore keys:
  - theme_source (system_dynamic | remote | local)
  - remote_url
  - use_dynamic_color (bool)
  - selected_font_family
  - selected_icon_style
  - icon_axes_weight, icon_axes_grade, icon_axes_opsz
  - last_theme_version, last_sync_time
- Cache:
  - theme.json (versioned)
  - font files (by family + variant)

---

## 12) Steps to Implement

1) Project Setup
- Add Material3, Hilt, DataStore, networking (e.g., OkHttp/Retrofit or Ktor), JSON parser (e.g., Moshi/Kotlinx), Google Fonts metadata access (HTTP), and icon resources dependencies as needed.
- Define min/target SDKs; enable Compose.

2) Domain Modeling
- Define ThemeTokens and sub-structures: colors (light/dark roles), typography, shapes, elevation, icons.
- Define ThemePreferences and ThemeModel (final material objects + metadata).
- Define repositories and use case interfaces.

3) Data Layer
- Implement RemoteThemeDataSource: fetch JSON; ETag/If-Modified-Since support.
- Implement LocalThemeDataSource: load bundled asset; read/write cached theme.json.
- Implement PreferencesDataSource with DataStore and keys.
- Implement FontsDataSource: resolve Google Fonts metadata; font file caching strategy.
- Implement IconsDataSource: constants for styles; optional axis caps.
- Implement mappers: JSON → ThemeTokens; validators (schemaVersion compatibility).
- Implement repositories: ThemeRepositoryImpl, FontRepositoryImpl, IconRepositoryImpl, PreferencesRepositoryImpl.

4) Use Cases
- Implement ValidateThemeJsonUseCase to verify schema and essential roles.
- Implement BuildMaterialThemeUseCase to produce ColorScheme, Typography, Shapes, Elevation from tokens + preferences + dynamic color provider.
- Implement ObserveThemeUseCase combining flows: preferences + tokens + font/icon configs + dynamic color.
- Implement SyncThemeUseCase with proper fallback and cache.
- Implement Update/Apply/Reset use cases.

5) DI (Hilt)
- Provide bindings for repositories, use cases, data sources, dispatchers.
- Provide singleton DataStore and HTTP client instances.
- Provide DynamicColorProvider.

6) Presentation Layer
- Create ThemeViewModel: wire use cases, expose ThemeUiState, handle intents.
- Build ThemeSettingsScreen (Compose):
  - Sections: source, color, typography, icons, shapes/elevation, persistence.
  - Hook up events to ViewModel; observe state.
- Build TypographyPickerScreen and IconStylePickerSheet; connect to ViewModel(s).
- Add ThemeSourceSelector dialog and Sync confirmation dialog.

7) App Theme Integration
- Wrap app content in a top-level MaterialTheme whose parameters are driven by a State<ThemeModel> from ObserveThemeUseCase.
- Provide preview-specific theme during selection; apply only when confirmed.

8) Animations
- Add crossfades/animated color changes on preview.
- Use Material motion transitions for navigation and sheets.

9) Error Handling & UX
- Add snackbar messages for sync success/failure; offline fallbacks.
- Show banners when schema invalid or version mismatch.
- Provide retry and reset actions.

10) Persistence & Caching
- Persist all selections into DataStore on Apply.
- Cache last valid theme.json and font files; purge on reset.

11) QA & Testing
- Unit tests for mappers and validators with malformed/missing tokens.
- Use case tests: dynamic color on/off, fallback selection.
- Repository tests with fake data sources (offline/online).
- UI tests: ensure previews change as expected and applied theme persists across process death.

12) Performance
- Ensure token parsing and font metadata fetch on IO dispatcher.
- Debounce sync actions; avoid heavy work on main thread.
- Lazy load fonts metadata only when typography picker opens.

---

## 13) Component Catalog (M3 Alignment)

- App Bars: Center-aligned TopAppBar in settings
- Buttons: Filled for Apply/Sync; Text for Cancel; Tonal for Reset
- Chips: Assist/Filter chips for color role previews
- Lists: Preference rows with icons and trailing switches
- Dialogs/Sheets: M3 modal with proper elevation tokens
- Sliders: For icon axes (weight/grade/opsz) where applicable
- Snackbars: For sync and error states
- Tabs: Not required for this feature
- Tooltips: Optional for explaining dynamic color and schema compatibility

---

## 14) Data Contracts & Validation Rules

- schemaVersion must be “1.x” (compatible range). Reject others.
- colors.light and colors.dark should include primary, onPrimary, background, surface, onSurface. Missing roles trigger partial fallback.
- typography.fontFamily must be a valid family name or resolvable fallback list.
- shapes corner radii must be non-negative.
- elevation levels monotonic non-decreasing.
- icons.materialSymbolsStyle in {filled, outlined, rounded}.

---

## 15) Non-Functional Requirements

- Reliability: Theme should remain stable offline; never block app launch.
- Performance: Theme recomposition under 16ms budget where possible; heavy work off main thread.
- Security: Only allow HTTPS sources; validate content type and size limits.
- Observability: Log sync outcomes, schema mismatches, and fallback usage (non-PII).
- Privacy: No user data collected; only theme assets.

---

This TDD describes the Dynamic Material Theme Sync feature end-to-end per Clean Architecture, ensuring automatic alignment with Material 3 and Figma Theme Builder updates, with robust state management, DI, persistence, and graceful UX under failure modes.