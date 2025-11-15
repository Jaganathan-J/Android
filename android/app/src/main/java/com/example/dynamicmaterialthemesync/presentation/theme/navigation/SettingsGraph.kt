package com.example.dynamicmaterialthemesync.presentation.theme.navigation

sealed class SettingsGraph(val route: String) {
    object SettingsRoot : SettingsGraph("settings_root")
}

sealed class SettingsDestination(val route: String) {
    object ThemeSettings : SettingsDestination("theme_settings")
    object TypographyPicker : SettingsDestination("typography_picker")
    object IconPicker : SettingsDestination("icon_picker")
    object ThemeSourceSelector : SettingsDestination("theme_source_selector")
    object ThemeSyncDeepLink : SettingsDestination("theme_sync_deeplink")
}