package com.fitlife.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fitlife.app.presentation.ui.onboarding.WelcomeScreen
import com.fitlife.app.presentation.ui.auth.LoginScreen
import com.fitlife.app.presentation.ui.profile.ProfileScreen
import com.fitlife.app.presentation.ui.workout.ExercisesScreen
import com.fitlife.app.presentation.ui.workout.ActiveWorkoutScreen
import com.fitlife.app.presentation.ui.workout.SummaryScreen

@Composable
fun FitLifeNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(onGetStarted = { navController.navigate(Screen.Login.route) })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable(Screen.Exercises.route) {
            ExercisesScreen(onBack = { navController.popBackStack() })
        }
        // Simulating flow: Profile(Workouts) -> Active -> Summary
        composable(Screen.ActiveWorkout.route) {
            ActiveWorkoutScreen(
                onBack = { navController.popBackStack() },
                onSkip = { navController.navigate(Screen.Summary.route) }
            )
        }
        composable(Screen.Summary.route) {
            SummaryScreen(
                onBack = {
                    navController.popBackStack(Screen.Profile.route, inclusive = false)
                }
            )
        }
    }
}