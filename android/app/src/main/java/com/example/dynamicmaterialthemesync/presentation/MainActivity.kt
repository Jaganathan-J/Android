package com.example.dynamicmaterialthemesync.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dynamicmaterialthemesync.presentation.theme.DynamicTheme
import com.example.dynamicmaterialthemesync.presentation.theme.LocalAppThemeModel
import com.example.dynamicmaterialthemesync.presentation.theme.ThemeModelProvider
import com.example.dynamicmaterialthemesync.presentation.theme.ThemeSettingsScreen
import com.example.dynamicmaterialthemesync.presentation.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemDark = isSystemInDarkTheme()
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val uiState by themeViewModel.uiState.collectAsState()

            DynamicTheme(
                isDarkTheme = systemDark,
                themeModel = uiState.themeModel
            ) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavHost(themeViewModel = themeViewModel)
                }
            }
        }
    }
}

@Composable
private fun AppNavHost(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "settings_root"
    ) {
        composable("settings_root") {
            ThemeSettingsScreen(
                uiStateFlow = themeViewModel.uiState,
                onIntent = themeViewModel::onIntent,
                navigateToTypographyPicker = {
                    navController.navigate("typography_picker")
                },
                navigateToIconPicker = {
                    navController.navigate("icon_picker")
                },
                navigateToThemeSourceSelector = {
                    navController.navigate("theme_source_selector")
                }
            )
        }
        composable("typography_picker") {
            TypographyPickerHost(onBack = { navController.popBackStack() })
        }
        composable("icon_picker") {
            IconStylePickerHost(onBack = { navController.popBackStack() })
        }
        composable("theme_source_selector") {
            ThemeSourceSelectorHost(onBack = { navController.popBackStack() })
        }
    }
}

@Composable
private fun TypographyPickerHost(onBack: () -> Unit) {
    // Placeholder for actual separate ViewModel; wired to ThemeViewModel in real app
    onBack()
}

@Composable
private fun IconStylePickerHost(onBack: () -> Unit) {
    onBack()
}

@Composable
private fun ThemeSourceSelectorHost(onBack: () -> Unit) {
    onBack()
}