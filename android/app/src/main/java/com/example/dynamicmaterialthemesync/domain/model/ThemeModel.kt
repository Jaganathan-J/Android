package com.example.dynamicmaterialthemesync.domain.model

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography

/**
 * Final theme model consumed by the presentation layer.
 */
data class ThemeModel(
    val colorScheme: ColorScheme,
    val typography: Typography,
    val shapes: Shapes,
    val elevationTokens: ElevationTokens?,
    val iconStyleConfig: IconStyleConfig,
    val themeSource: ThemeSource,
    val useDynamicColor: Boolean,
    val supportsDynamicColor: Boolean,
    val themeVersion: String?,
    val lastSyncTimeMillis: Long?
)