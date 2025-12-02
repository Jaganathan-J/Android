package com.example.fitlife.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitlife.ui.feature.auth.LoginScreen
import com.example.fitlife.ui.feature.execution.ActiveWorkoutScreen
import com.example.fitlife.ui.feature.execution.SummaryScreen
import com.example.fitlife.ui.feature.onboarding.WelcomeScreen
import com.example.fitlife.ui.feature.profile.ProfileScreen
import com.example.fitlife.ui.feature.settings.SettingsScreen
import com.example.fitlife.ui.feature.workout.CategoriesScreen

@Composable
fun FitLifeNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(Screen.Categories.route) {
            CategoriesScreen(navController)
        }
        composable(Screen.WorkoutList.route) {
            // Reusing Categories or navigating to active directly for simplicity of this demo flow
            ActiveWorkoutScreen(navController)
        }
        composable(Screen.ActiveWorkout.route) {
            ActiveWorkoutScreen(navController)
        }
        composable(Screen.Summary.route) {
            SummaryScreen(navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
    }
}