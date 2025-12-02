package com.example.fitlife.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitlife.presentation.screens.auth.LoginScreen
import com.example.fitlife.presentation.screens.execution.ActiveProgressScreen
import com.example.fitlife.presentation.screens.execution.SummaryScreen
import com.example.fitlife.presentation.screens.home.ProfileScreen
import com.example.fitlife.presentation.screens.onboarding.WelcomeScreen
import com.example.fitlife.presentation.screens.settings.SettingsScreen
import com.example.fitlife.presentation.screens.workout.ExerciseCategoriesScreen
import com.example.fitlife.presentation.screens.workout.ExerciseListScreen

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onGetStarted = { navController.navigate("login") }
            )
        }
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("profile") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("profile") {
            ProfileScreen(
                onNavigateToWorkouts = { navController.navigate("categories") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        composable("settings") {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable("categories") {
            ExerciseCategoriesScreen(
                onCategorySelected = { id -> navController.navigate("exercises/$id") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("exercises/{categoryId}") {
            ExerciseListScreen(
                onExerciseSelected = { id -> navController.navigate("active/$id") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("active/{exerciseId}") {
            ActiveProgressScreen(
                onFinish = { navController.navigate("summary") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("summary") {
            SummaryScreen(
                onHome = { 
                    navController.navigate("profile") {
                        popUpTo("profile") { inclusive = true }
                    }
                }
            )
        }
    }
}