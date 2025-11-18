package com.example.dynamicmaterialthemesync.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.example.dynamicmaterialthemesync.domain.model.ColorTokens
import com.example.dynamicmaterialthemesync.domain.model.ThemeData

private fun String.toColorOrNull(): Color? = try {
    Color(android.graphics.Color.parseColor(this))
} catch (_: IllegalArgumentException) {
    null
}

fun ThemeData.toLightColorSchemeOrNull(): ColorScheme? {
    val tokens: ColorTokens = this.colors ?: return null
    val primary = tokens.primary?.toColorOrNull() ?: return null
    return lightColorScheme(
        primary = primary,
        onPrimary = tokens.onPrimary?.toColorOrNull() ?: Color.White,
        secondary = tokens.secondary?.toColorOrNull() ?: primary,
        background = tokens.background?.toColorOrNull() ?: Color(0xFFFFFFFF),
        surface = tokens.surface?.toColorOrNull() ?: Color(0xFFFFFFFF),
        primaryContainer = tokens.primaryContainer?.toColorOrNull() ?: primary.copy(alpha = 0.12f),
        onBackground = tokens.onBackground?.toColorOrNull() ?: Color(0xFF000000)
    )
}

fun ThemeData.toDarkColorSchemeOrNull(): ColorScheme? {
    val tokens: ColorTokens = this.colors ?: return null
    val primary = tokens.primary?.toColorOrNull() ?: return null
    return darkColorScheme(
        primary = primary,
        onPrimary = tokens.onPrimary?.toColorOrNull() ?: Color.Black,
        secondary = tokens.secondary?.toColorOrNull() ?: primary,
        background = tokens.background?.toColorOrNull() ?: Color(0xFF000000),
        surface = tokens.surface?.toColorOrNull() ?: Color(0xFF121212),
        primaryContainer = tokens.primaryContainer?.toColorOrNull() ?: primary.copy(alpha = 0.24f),
        onBackground = tokens.onBackground?.toColorOrNull() ?: Color(0xFFFFFFFF)
    )
}