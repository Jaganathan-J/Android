package com.example.dynamicmaterialthemesync.domain.model

import androidx.compose.ui.text.TextStyle

/**
 * Simplified container for Figma typography tokens mapped to Compose TextStyle.
 */
data class ThemeTypographyToken(
    val name: String,
    val style: TextStyle
)