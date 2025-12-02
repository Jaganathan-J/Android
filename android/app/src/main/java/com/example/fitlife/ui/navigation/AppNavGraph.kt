package com.example.fitlife.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitlife.ui.screens.*

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(onGetStarted = { navController.navigate("login") })
        }
        composable("login") {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onLoginSuccess = { navController.navigate("profile") }
            )
        }
        composable("profile") {
            ProfileScreen(
                onNavigateTo = { route -> 
                    if(route == "back") navController.popBackStack() else navController.navigate(route) 
                }
            )
        }
        composable("workouts") {
            ExerciseCategoriesScreen(
                onBack = { navController.popBackStack() },
                onCategoryClick = { catId -> navController.navigate("workout_list/$catId") }
            )
        }
        composable("workout_list/{categoryId}") {
            WorkoutListScreen(
                onBack = { navController.popBackStack() },
                onRoutineClick = { routineId -> navController.navigate("routine_detail/$routineId") }
            )
        }
        composable("routine_detail/{routineId}") {
            RoutineDetailScreen(
                onBack = { navController.popBackStack() },
                onExerciseClick = { exId -> navController.navigate("active_workout/$exId") }
            )
        }
        composable("active_workout/{exerciseId}") {
            ActiveWorkoutScreen(
                onBack = { navController.popBackStack() },
                onFinish = { navController.navigate("summary") }
            )
        }
        composable("summary") {
            SummaryScreen(
                onDone = { navController.navigate("profile") { popUpTo("profile") { inclusive = true } } }
            )
        }
        composable("settings") {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}