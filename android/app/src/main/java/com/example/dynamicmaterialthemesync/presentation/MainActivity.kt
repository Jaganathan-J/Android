package com.example.dynamicmaterialthemesync.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dynamicmaterialthemesync.presentation.theme.AppDynamicTheme
import com.example.dynamicmaterialthemesync.presentation.theme.navigation.SettingsDestination
import com.example.dynamicmaterialthemesync.presentation.theme.navigation.SettingsGraph
import com.example.dynamicmaterialthemesync.presentation.theme.screen.ThemeSettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootApp()
        }
    }
}

@Composable
private fun RootApp() {
    val navController = rememberNavController()
    AppDynamicTheme {
        NavHost(
            navController = navController,
            startDestination = SettingsGraph.SettingsRoot.route
        ) {
            composable(route = SettingsGraph.SettingsRoot.route) {
                val viewModel: ThemeViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsState()
                ThemeSettingsScreen(
                    uiState = uiState,
                    onIntent = { intent -> viewModel.onIntent(intent) },
                    onNavigateTypographyPicker = {
                        navController.navigate(SettingsDestination.TypographyPicker.route)
                    },
                    onNavigateIconPicker = {
                        navController.navigate(SettingsDestination.IconPicker.route)
                    },
                    onNavigateThemeSourceSelector = {
                        navController.navigate(SettingsDestination.ThemeSourceSelector.route)
                    }
                )
            }

            composable(route = SettingsDestination.TypographyPicker.route) {
                TypographyPickerHost(onBack = { navController.popBackStack() })
            }

            composable(route = SettingsDestination.IconPicker.route) {
                IconStylePickerHost(onBack = { navController.popBackStack() })
            }

            composable(route = SettingsDestination.ThemeSourceSelector.route) {
                ThemeSourceSelectorHost(onBack = { navController.popBackStack() })
            }

            composable(
                route = SettingsDestination.ThemeSyncDeepLink.route + "?syncNow={syncNow}",
                arguments = listOf(
                    navArgument("syncNow") { type = NavType.BoolType; defaultValue = false }
                )
            ) { backStackEntry ->
                val syncNow = backStackEntry.arguments?.getBoolean("syncNow") ?: false
                val viewModel: ThemeViewModel = hiltViewModel()
                if (syncNow) {
                    viewModel.onIntent(ThemeIntent.RequestSync)
                }
                val uiState by viewModel.uiState.collectAsState()
                ThemeSettingsScreen(
                    uiState = uiState,
                    onIntent = { intent -> viewModel.onIntent(intent) },
                    onNavigateTypographyPicker = {
                        navController.navigate(SettingsDestination.TypographyPicker.route)
                    },
                    onNavigateIconPicker = {
                        navController.navigate(SettingsDestination.IconPicker.route)
                    },
                    onNavigateThemeSourceSelector = {
                        navController.navigate(SettingsDestination.ThemeSourceSelector.route)
                    }
                )
            }
        }
    }
}