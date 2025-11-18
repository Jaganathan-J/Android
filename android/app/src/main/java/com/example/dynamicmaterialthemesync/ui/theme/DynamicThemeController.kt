package com.example.dynamicmaterialthemesync.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.presentation.home.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Stable
class DynamicThemeController internal constructor(initialState: ThemeState) {

    private val _themeState = MutableStateFlow(initialState)
    val themeState: StateFlow<ThemeState> = _themeState.asStateFlow()

    fun updateFromPreferences(preferences: ThemePreferences) {
        _themeState.value = _themeState.value.copy(preferences = preferences)
    }

    fun updateRemoteThemes(light: androidx.compose.material3.ColorScheme?, dark: androidx.compose.material3.ColorScheme?) {
        _themeState.value = _themeState.value.copy(
            remoteThemeLight = light,
            remoteThemeDark = dark
        )
    }
}

val LocalDynamicThemeController = staticCompositionLocalOf<DynamicThemeController> {
    error("DynamicThemeController not provided")
}

@Composable
fun rememberDynamicThemeController(): DynamicThemeController {
    return remember {
        DynamicThemeController(ThemeState())
    }
}