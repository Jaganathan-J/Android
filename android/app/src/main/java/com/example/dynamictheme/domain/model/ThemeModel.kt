package com.example.dynamictheme.domain.model

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import com.example.dynamictheme.domain.model.enums.IconStyle
import com.example.dynamictheme.domain.model.enums.ThemeSource

data class ThemeModel(
    val colorScheme: ColorScheme,
    val typography: Typography,
    val shapes: Shapes,
    val elevationTokens: ElevationTokens?,
    val iconStyle: IconStyle,
    val iconWeight: Int?,
    val iconGrade: Int?,
    val iconOpticalSize: Int?,
    val themeSource: ThemeSource,
    val useDynamicColor: Boolean,
    val supportsDynamicColor: Boolean,
    val themeVersion: String?,
    val lastSyncTimeMillis: Long?
) {
    companion object {
        fun default(): ThemeModel {
            val defaultTypography = Typography()
            val defaultShapes = Shapes()
            val defaultColorScheme = androidx.compose.material3.lightColorScheme()
            return ThemeModel(
                colorScheme = defaultColorScheme,
                typography = defaultTypography,
                shapes = defaultShapes,
                elevationTokens = null,
                iconStyle = IconStyle.Filled,
                iconWeight = null,
                iconGrade = null,
                iconOpticalSize = null,
                themeSource = ThemeSource.SystemDynamic,
                useDynamicColor = true,
                supportsDynamicColor = false,
                themeVersion = null,
                lastSyncTimeMillis = null
            )
        }
    }
}