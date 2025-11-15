package com.example.dynamicmaterialthemesync.domain.model

import androidx.compose.ui.text.font.FontFamily

/**
 * Represents resolved font configuration for the app typography.
 */
data class FontConfig(
    val familyName: String,
    val fontFamily: FontFamily,
    val isVariable: Boolean = false,
    val availableAxes: List<String> = emptyList()
)

data class FontOption(
    val name: String,
    val source: String,
    val isDownloaded: Boolean
)