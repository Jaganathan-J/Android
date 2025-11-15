package com.example.dynamicmaterialthemesync.presentation.theme

import android.os.Build
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.example.dynamicmaterialthemesync.core.theme.ThemeMode
import com.example.dynamicmaterialthemesync.presentation.viewmodel.ThemeViewModel

@Composable
fun DynamicMaterialTheme(
    viewModel: ThemeViewModel,
    content: @Composable () -> Unit
) {
    val themeState by viewModel.themeState.collectAsState()

    val systemDark = isSystemInDarkTheme()
    val isDark = when (themeState.mode) {
        ThemeMode.SYSTEM -> systemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = createColorSchemeFromTokens(isDark = isDark)

    Crossfade(targetState = colorScheme, label = "theme-crossfade") { scheme ->
        MaterialTheme(
            colorScheme = scheme,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
            content = content
        )
    }
}

private fun createColorSchemeFromTokens(isDark: Boolean): ColorScheme {
    // For brevity, we are not mapping actual tokens here; in a real
    // implementation, you would read from ThemeTokens in ThemeViewModel state.
    return if (isDark) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }
}