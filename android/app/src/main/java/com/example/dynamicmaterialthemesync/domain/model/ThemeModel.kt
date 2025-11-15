package com.example.dynamicmaterialthemesync.domain.model

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Final model consumed by UI/theme layer */

data class ThemeModel(
    val colorSchemeLight: ColorScheme,
    val colorSchemeDark: ColorScheme,
    val typography: Typography,
    val shapes: Shapes,
    val elevation: ElevationLevels,
    val iconStyleConfig: IconStyleConfig,
    val themeSource: ThemeSource,
    val themeVersion: String?,
    val lastSyncTimeMillis: Long?,
    val supportsDynamicColor: Boolean,
    val useDynamicColor: Boolean
) {
    companion object {
        fun default(isDark: Boolean): ThemeModel {
            val colorScheme = if (isDark) DefaultThemePresets.darkColorScheme() else DefaultThemePresets.lightColorScheme()
            return ThemeModel(
                colorSchemeLight = DefaultThemePresets.lightColorScheme(),
                colorSchemeDark = DefaultThemePresets.darkColorScheme(),
                typography = DefaultThemePresets.typography(),
                shapes = DefaultThemePresets.shapes(),
                elevation = DefaultThemePresets.elevation(),
                iconStyleConfig = IconStyleConfig(),
                themeSource = ThemeSource(ThemeSourceType.SYSTEM_DYNAMIC),
                themeVersion = null,
                lastSyncTimeMillis = null,
                supportsDynamicColor = false,
                useDynamicColor = false
            )
        }
    }
}

data class ElevationLevels(
    val level0: Float,
    val level1: Float,
    val level2: Float,
    val level3: Float,
    val level4: Float,
    val level5: Float
)

object DefaultThemePresets {
    fun lightColorScheme(): ColorScheme = androidx.compose.material3.lightColorScheme()

    fun darkColorScheme(): ColorScheme = androidx.compose.material3.darkColorScheme()

    fun typography(): Typography = androidx.compose.material3.Typography()

    fun shapes(): Shapes = androidx.compose.material3.Shapes(
        extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(4.dp),
        small = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
        medium = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        large = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(28.dp)
    )

    fun elevation(): ElevationLevels = ElevationLevels(
        level0 = 0f,
        level1 = 1f,
        level2 = 3f,
        level3 = 6f,
        level4 = 8f,
        level5 = 12f
    )
}