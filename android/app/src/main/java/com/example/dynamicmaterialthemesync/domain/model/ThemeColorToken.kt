package com.example.dynamicmaterialthemesync.domain.model

import androidx.compose.ui.graphics.Color

/**
 * Represents a single mapped color token from Figma Theme Builder
 */
data class ThemeColorToken(
    val name: String,
    val light: Color,
    val dark: Color
)