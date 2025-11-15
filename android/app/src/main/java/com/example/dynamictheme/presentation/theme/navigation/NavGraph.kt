package com.example.dynamictheme.presentation.theme.navigation

import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dynamictheme.presentation.theme.screen.ThemeSettingsScreen
import com.example.dynamictheme.presentation.theme.viewmodel.ThemeViewModel

object Destinations {
    const val HOME = "home"
    const val THEME_SETTINGS = "theme_settings"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    snackbarHost: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.THEME_SETTINGS,
        modifier = modifier
    ) {
        composable(Destinations.THEME_SETTINGS) {
            val vm: ThemeViewModel = hiltViewModel()
            ThemeSettingsScreen(
                viewModel = vm,
                snackbarHost = snackbarHost
            )
        }
    }
}