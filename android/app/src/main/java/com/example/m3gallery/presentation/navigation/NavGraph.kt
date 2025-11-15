package com.example.m3gallery.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.m3gallery.presentation.screens.buttons.ButtonsScreen
import com.example.m3gallery.presentation.screens.cards.CardsScreen
import com.example.m3gallery.presentation.screens.dialogs.DialogsScreen
import com.example.m3gallery.presentation.screens.home.HomeScreen
import com.example.m3gallery.presentation.screens.settings.SettingsScreen

@Composable
fun M3NavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(innerPadding = innerPadding, onNavigate = { navController.navigate(it) })
        }
        composable(Screen.Buttons.route) {
            ButtonsScreen(innerPadding = innerPadding)
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
