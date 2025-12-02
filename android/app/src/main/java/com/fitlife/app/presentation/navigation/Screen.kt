package com.fitlife.app.presentation.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Profile : Screen("profile")
    object Exercises : Screen("exercises")
    object ActiveWorkout : Screen("workout/active")
    object Summary : Screen("workout/summary")
}