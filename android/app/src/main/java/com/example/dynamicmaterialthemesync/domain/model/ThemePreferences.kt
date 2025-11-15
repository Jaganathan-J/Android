package com.example.dynamicmaterialthemesync.domain.model

enum class ThemeSourceType {
    SYSTEM_DYNAMIC,
    REMOTE,
    LOCAL
}

data class ThemeSource(
    val type: ThemeSourceType,
    val remoteUrl: String? = null,
    val localAssetName: String? = null
)

data class ThemePreferences(
    val themeSource: ThemeSource = ThemeSource(ThemeSourceType.SYSTEM_DYNAMIC),
    val useDynamicColor: Boolean = true,
    val selectedFontFamily: String? = null,
    val selectedIconStyle: IconStyle = IconStyle.FILLED,
    val iconAxes: IconAxes = IconAxes(),
    val applyOnLaunch: Boolean = true,
    val lastThemeVersion: String? = null,
    val lastSyncTimeMillis: Long? = null
)

data class FontConfig(
    val family: String,
    val fallback: List<String> = emptyList(),
    val source: FontSource = FontSource.THEME_JSON
)

enum class FontSource {
    THEME_JSON,
    GOOGLE_FONTS,
    LOCAL_FALLBACK
}

enum class IconStyle {
    FILLED,
    OUTLINED,
    ROUNDED
}

data class IconAxes(
    val weight: Int = 400,
    val grade: Int = 0,
    val opticalSize: Int = 24
)

data class IconStyleConfig(
    val style: IconStyle = IconStyle.FILLED,
    val axes: IconAxes = IconAxes()
)

data class SyncInfo(
    val lastSyncTimeMillis: Long?,
    val themeVersion: String?
)