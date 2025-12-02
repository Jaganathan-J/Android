package com.example.fitlife.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Profile : Screen("profile")
    object Categories : Screen("categories")
    object WorkoutList : Screen("workout_list/{categoryId}") {
        fun createRoute(categoryId: String) = "workout_list/$categoryId"
    }
    object ActiveWorkout : Screen("active")
    object Summary : Screen("summary")
    object Settings : Screen("settings")
}