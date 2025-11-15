package com.example.dynamicmaterialthemesync.domain.model

enum class ThemeSource {
    SYSTEM_DYNAMIC,
    REMOTE,
    LOCAL
}

data class ThemePreferences(
    val themeSource: ThemeSource = ThemeSource.SYSTEM_DYNAMIC,
    val remoteUrl: String? = null,
    val useDynamicColor: Boolean = true,
    val applyOnLaunch: Boolean = true,
    val selectedFontFamily: String? = null,
    val selectedIconStyle: IconStyle = IconStyle.FILLED,
    val iconWeight: Int? = null,
    val iconGrade: Int? = null,
    val iconOpticalSize: Int? = null,
    val lastThemeVersion: String? = null,
    val lastSyncTimeMillis: Long? = null
)