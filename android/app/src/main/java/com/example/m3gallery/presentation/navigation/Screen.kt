package com.example.m3gallery.presentation.navigation

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Buttons : Screen("buttons", "Buttons")
    object Cards : Screen("cards", "Cards")
    object Dialogs : Screen("dialogs", "Dialogs")
    object Settings : Screen("settings", "Settings")
}
