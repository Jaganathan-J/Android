package com.example.dynamicmaterialthemesync.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dynamicmaterialthemesync.presentation.home.HomeRoute
import com.example.dynamicmaterialthemesync.presentation.profile.ProfileRoute
import com.example.dynamicmaterialthemesync.presentation.settings.ThemeSettingsRoute

object Destinations {
    const val HOME = "home"
    const val SETTINGS = "settings"
    const val PROFILE = "profile"
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Destinations.HOME) {
        composable(Destinations.HOME) {
            HomeRoute(
                onNavigateToSettings = { navController.navigate(Destinations.SETTINGS) },
                onNavigateToProfile = { navController.navigate(Destinations.PROFILE) }
            )
        }
        composable(Destinations.SETTINGS) {
            ThemeSettingsRoute(onBackClick = { navController.popBackStack() })
        }
        composable(Destinations.PROFILE) {
            ProfileRoute(onBackClick = { navController.popBackStack() })
        }
    }
}