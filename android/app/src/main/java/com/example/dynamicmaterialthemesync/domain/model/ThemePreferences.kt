package com.example.dynamicmaterialthemesync.domain.model

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

data class ThemePreferences(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val useDynamicColor: Boolean = true,
    val useRemoteTheme: Boolean = true
)