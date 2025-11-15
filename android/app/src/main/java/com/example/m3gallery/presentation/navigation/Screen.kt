package com.example.m3gallery.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ComponentsList : Screen("components_list")
    object Buttons : Screen("buttons")
    object Cards : Screen("cards")
    object Dialogs : Screen("dialogs")
    object Settings : Screen("settings")
}
