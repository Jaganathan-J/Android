package com.example.fitlife.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitlife.presentation.screens.active.ActiveWorkoutScreen
import com.example.fitlife.presentation.screens.auth.LoginScreen
import com.example.fitlife.presentation.screens.exercises.CategoryListScreen
import com.example.fitlife.presentation.screens.exercises.RoutineDetailsScreen
import com.example.fitlife.presentation.screens.exercises.RoutineListScreen
import com.example.fitlife.presentation.screens.onboarding.WelcomeScreen
import com.example.fitlife.presentation.screens.profile.ProfileScreen
import com.example.fitlife.presentation.screens.settings.SettingsScreen
import com.example.fitlife.presentation.screens.summary.SummaryScreen

object Routes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val PROFILE = "profile"
    const val WORKOUT_CATEGORIES = "workout_categories"
    const val ROUTINE_LIST = "routine_list/{catId}"
    const val ROUTINE_DETAILS = "routine_details/{routineId}"
    const val ACTIVE_WORKOUT = "active_workout/{exerciseId}"
    const val SUMMARY = "summary"
    const val SETTINGS = "settings"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.WELCOME) {
        composable(Routes.WELCOME) {
            WelcomeScreen(onGetStarted = { navController.navigate(Routes.LOGIN) })
        }
        composable(Routes.LOGIN) {
            LoginScreen(onLoginSuccess = { navController.navigate(Routes.PROFILE) })
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onNavigateToWorkouts = { navController.navigate(Routes.WORKOUT_CATEGORIES) },
                onNavigateToExercises = { navController.navigate(Routes.WORKOUT_CATEGORIES) }, // Reusing for demo
                onNavigateToProgress = { /* TODO */ },
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }
        composable(Routes.WORKOUT_CATEGORIES) {
            CategoryListScreen(
                onNavigateBack = { navController.popBackStack() },
                onCategoryClick = { catId -> navController.navigate("routine_list/$catId") }
            )
        }
        composable(
            route = Routes.ROUTINE_LIST,
            arguments = listOf(navArgument("catId") { type = NavType.StringType })
        ) {
            RoutineListScreen(
                onNavigateBack = { navController.popBackStack() },
                onRoutineClick = { routineId -> navController.navigate("routine_details/$routineId") }
            )
        }
        composable(
            route = Routes.ROUTINE_DETAILS,
            arguments = listOf(navArgument("routineId") { type = NavType.StringType })
        ) {
            RoutineDetailsScreen(
                onNavigateBack = { navController.popBackStack() },
                onExerciseStart = { exerciseId -> navController.navigate("active_workout/$exerciseId") }
            )
        }
        composable(
            route = Routes.ACTIVE_WORKOUT,
            arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })
        ) {
            ActiveWorkoutScreen(
                onNavigateBack = { navController.popBackStack() },
                onFinish = { navController.navigate(Routes.SUMMARY) }
            )
        }
        composable(Routes.SUMMARY) {
            SummaryScreen(
                onDone = { 
                   navController.popBackStack(Routes.PROFILE, inclusive = false) 
                }
            )
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}