package com.example.m3gallery.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Buttons : Screen("buttons")
    data object Cards : Screen("cards")
    data object Dialogs : Screen("dialogs")
    data object Settings : Screen("settings")
}
