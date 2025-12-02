package com.example.fitlife.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitlife.presentation.screens.auth.LoginScreen
import com.example.fitlife.presentation.screens.execution.ActiveSessionScreen
import com.example.fitlife.presentation.screens.onboarding.WelcomeScreen
import com.example.fitlife.presentation.screens.profile.ProfileScreen
import com.example.fitlife.presentation.screens.settings.SettingsScreen
import com.example.fitlife.presentation.screens.workout.CategoryListScreen

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(onGetStarted = { navController.navigate("login") })
        }
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("profile") {
                        popUpTo("welcome") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("profile") {
            ProfileScreen(
                onNavigateToWorkouts = { navController.navigate("categories") },
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToExercises = { /* User Story S07 */ },
                onNavigateToProgress = { /* User Story S09 */ }
            )
        }
        composable("settings") {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable("categories") {
            CategoryListScreen(
                onBack = { navController.popBackStack() },
                onCategorySelected = { catId ->
                    // Direct jump to execution for demo purposes, normally goes to detail list
                    navController.navigate("active_session/ex1")
                }
            )
        }
        composable("active_session/{exerciseId}") {
            ActiveSessionScreen(
                onBack = { navController.popBackStack() },
                onSummary = { navController.popBackStack() }
            )
        }
    }
}