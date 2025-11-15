package com.example.dynamicthemesync.domain.model

import androidx.compose.ui.graphics.Color

data class ThemeTokens(
    val schemaVersion: String,
    val themeVersion: String,
    val name: String,
    val colors: ColorTokens?,
    val typography: TypographyTokens?,
    val shapes: ShapeTokens?,
    val elevation: ElevationTokens?,
    val icons: IconTokens?
)

data class ColorTokens(
    val light: ColorRoles?,
    val dark: ColorRoles?
)

data class ColorRoles(
    val primary: Color?,
    val onPrimary: Color?,
    val primaryContainer: Color?,
    val onPrimaryContainer: Color?,
    val secondary: Color?,
    val onSecondary: Color?,
    val background: Color?,
    val onBackground: Color?,
    val surface: Color?,
    val onSurface: Color?,
    val surfaceVariant: Color?,
    val onSurfaceVariant: Color?,
    val tertiary: Color?,
    val error: Color?,
    val onError: Color?,
    val outline: Color?,
    val inverseSurface: Color?,
    val inverseOnSurface: Color?
)

data class TypographyTokens(
    val fontFamily: String?,
    val fallback: List<String>,
    val weightMapping: Map<String, Int>,
    val sizeSp: Map<String, Float>,
    val lineHeightSp: Map<String, Float>,
    val letterSpacingEm: Map<String, Float>
)

data class ShapeTokens(
    val cornerFamily: String?,
    val extraSmall: Float?,
    val small: Float?,
    val medium: Float?,
    val large: Float?,
    val extraLarge: Float?
)

data class ElevationTokens(
    val level0: Float?,
    val level1: Float?,
    val level2: Float?,
    val level3: Float?,
    val level4: Float?,
    val level5: Float?
)

data class IconTokens(
    val materialSymbolsStyle: IconStyle,
    val defaultWeight: Int,
    val defaultGrade: Int,
    val defaultOpticalSize: Int
)

enum class IconStyle { FILLED, OUTLINED, ROUNDED }

enum class ThemeSourceType { SYSTEM_DYNAMIC, REMOTE, LOCAL }

data class ThemePreferences(
    val themeSource: ThemeSourceType,
    val remoteUrl: String?,
    val useDynamicColor: Boolean,
    val selectedFontFamily: String?,
    val selectedIconStyle: IconStyle,
    val iconWeight: Int?,
    val iconGrade: Int?,
    val iconOpticalSize: Int?,
    val lastThemeVersion: String?,
    val lastSyncTimeMillis: Long?
)

data class FontConfig(
    val family: String,
    val fallbacks: List<String>
)

data class IconStyleConfig(
    val style: IconStyle,
    val weight: Int,
    val grade: Int,
    val opticalSize: Int
)

data class ThemeModel(
    val colorSchemeLight: androidx.compose.material3.ColorScheme,
    val colorSchemeDark: androidx.compose.material3.ColorScheme,
    val typography: androidx.compose.material3.Typography,
    val shapes: androidx.compose.material3.Shapes,
    val elevation: ElevationTokens,
    val iconStyleConfig: IconStyleConfig,
    val preferences: ThemePreferences,
    val themeName: String,
    val themeVersion: String?
) {
    companion object {
        fun default(): ThemeModel {
            val defaultPref = ThemePreferences(
                themeSource = ThemeSourceType.SYSTEM_DYNAMIC,
                remoteUrl = null,
                useDynamicColor = true,
                selectedFontFamily = null,
                selectedIconStyle = IconStyle.OUTLINED,
                iconWeight = null,
                iconGrade = null,
                iconOpticalSize = null,
                lastThemeVersion = null,
                lastSyncTimeMillis = null
            )
            val elevationTokens = ElevationTokens(0f, 1f, 3f, 6f, 8f, 12f)
            val iconConfig = IconStyleConfig(IconStyle.OUTLINED, 400, 0, 24)
            return ThemeModel(
                colorSchemeLight = androidx.compose.material3.lightColorScheme(),
                colorSchemeDark = androidx.compose.material3.darkColorScheme(),
                typography = androidx.compose.material3.Typography(),
                shapes = androidx.compose.material3.Shapes(),
                elevation = elevationTokens,
                iconStyleConfig = iconConfig,
                preferences = defaultPref,
                themeName = "Default",
                themeVersion = null
            )
        }
    }
}

data class SyncInfo(
    val lastSyncTimeMillis: Long?,
    val themeVersion: String?
)