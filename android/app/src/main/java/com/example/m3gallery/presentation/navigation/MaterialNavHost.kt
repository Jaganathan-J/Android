package com.example.m3gallery.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.m3gallery.presentation.screens.buttons.ButtonsScreenRoot
import com.example.m3gallery.presentation.screens.cards.CardsScreen
import com.example.m3gallery.presentation.screens.dialogs.DialogsScreen
import com.example.m3gallery.presentation.screens.home.HomeScreen
import com.example.m3gallery.presentation.screens.settings.SettingsScreen

@Composable
fun MaterialNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                innerPadding = innerPadding,
                onNavigateToButtons = { navController.navigate(Screen.Buttons.route) },
                onNavigateToCards = { navController.navigate(Screen.Cards.route) },
                onNavigateToDialogs = { navController.navigate(Screen.Dialogs.route) }
            )
        }

        composable(Screen.Buttons.route) {
            ButtonsScreenRoot(innerPadding = innerPadding)
        }

        composable(Screen.Cards.route) {
            CardsScreen(innerPadding = innerPadding)
        }

        composable(Screen.Dialogs.route) {
            DialogsScreen(innerPadding = innerPadding)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(innerPadding = innerPadding)
        }
    }
}
