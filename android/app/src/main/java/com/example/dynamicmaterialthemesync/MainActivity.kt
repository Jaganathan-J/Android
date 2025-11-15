package com.example.dynamicmaterialthemesync

import android.os.Build
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
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dynamicmaterialthemesync.domain.model.ThemeModel
import com.example.dynamicmaterialthemesync.presentation.theme.ThemeSettingsScreen
import com.example.dynamicmaterialthemesync.presentation.theme.ThemeViewModel
import com.example.dynamicmaterialthemesync.presentation.theme.TypographyPickerScreen
import com.example.dynamicmaterialthemesync.presentation.theme.IconStylePickerSheet
import com.example.dynamicmaterialthemesync.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val rootViewModel: ThemeViewModel = hiltViewModel()
            val uiState by rootViewModel.uiState.collectAsState()
            val isDark = isSystemInDarkTheme()

            val themeModel = remember(uiState.effectiveThemeModel, isDark) {
                uiState.effectiveThemeModel ?: ThemeModel.default(isDark)
            }

            AppTheme(themeModel = themeModel, useDarkTheme = isDark) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "themeSettings"
    ) {
        composable("themeSettings") {
            val viewModel: ThemeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            ThemeSettingsScreen(
                uiState = uiState,
                onIntent = viewModel::onIntent,
                onNavigateToTypographyPicker = {
                    navController.navigate("typographyPicker")
                },
                onNavigateToIconPicker = {
                    navController.navigate("iconPicker")
                }
            )
        }
        composable("typographyPicker") {
            val viewModel: ThemeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            TypographyPickerScreen(
                uiState = uiState,
                onSelectFont = { font ->
                    viewModel.onSelectTypography(font)
                },
                onApply = {
                    viewModel.onApplyChanges()
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }
        composable("iconPicker") {
            val viewModel: ThemeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            IconStylePickerSheet(
                uiState = uiState,
                onSelectStyle = { style -> viewModel.onSelectIconStyle(style) },
                onApply = {
                    viewModel.onApplyChanges()
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}