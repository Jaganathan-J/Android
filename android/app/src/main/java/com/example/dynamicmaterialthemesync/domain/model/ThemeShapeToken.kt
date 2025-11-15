package com.example.dynamicmaterialthemesync.domain.model

import androidx.compose.foundation.shape.CornerBasedShape

/**
 * Simplified container for shape tokens.
 */
data class ThemeShapeToken(
    val name: String,
    val shape: CornerBasedShape
)