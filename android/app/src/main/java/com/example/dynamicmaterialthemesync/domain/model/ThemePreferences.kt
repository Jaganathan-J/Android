package com.example.dynamicmaterialthemesync.domain.model

import kotlinx.coroutines.flow.Flow

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
    val themeSource: ThemeSource,
    val useDynamicColor: Boolean,
    val selectedFontFamily: String?,
    val iconStyle: IconStyle,
    val iconWeight: Int?,
    val iconGrade: Int?,
    val iconOpticalSize: Int?,
    val applyOnLaunch: Boolean,
    val lastThemeVersion: String?,
    val lastSyncTimeMillis: Long?
)

data class FontConfig(
    val family: String,
    val fallbackFamilies: List<String> = emptyList()
)

data class IconStyleConfig(
    val style: IconStyle,
    val weight: Int?,
    val grade: Int?,
    val opticalSize: Int?
)

data class ThemeModel(
    val colorSchemeLight: androidx.compose.material3.ColorScheme,
    val colorSchemeDark: androidx.compose.material3.ColorScheme,
    val typography: androidx.compose.material3.Typography,
    val shapes: androidx.compose.material3.Shapes,
    val elevationTokens: ElevationTokens,
    val iconStyleConfig: IconStyleConfig,
    val themeSource: ThemeSource,
    val themeVersion: String?,
    val lastSyncTimeMillis: Long?
)