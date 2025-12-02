package com.fitlife.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fitlife.app.presentation.screens.auth.LoginScreen
import com.fitlife.app.presentation.screens.execution.ActiveProgressScreen
import com.fitlife.app.presentation.screens.home.ProfileScreen
import com.fitlife.app.presentation.screens.onboarding.WelcomeScreen
import com.fitlife.app.presentation.screens.workout.ExerciseCategoriesScreen

object Routes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val PROFILE = "profile"
    const val EXERCISE_CATEGORIES = "exercise_categories"
    const val ACTIVE_PROGRESS = "active_progress"
}

@Composable
fun FitLifeNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.WELCOME) {
        composable(Routes.WELCOME) {
            WelcomeScreen(onGetStarted = { navController.navigate(Routes.LOGIN) })
        }
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { 
                    navController.navigate(Routes.PROFILE) { 
                        popUpTo(Routes.WELCOME) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() } // Per wireframe functionality
            )
        }
        composable(Routes.EXERCISE_CATEGORIES) {
            ExerciseCategoriesScreen(
                onBack = { navController.popBackStack() },
                onCategoryClick = { 
                    // Would navigate to detail list, but routing to active timer for demo flow
                    navController.navigate(Routes.ACTIVE_PROGRESS)
                }
            )
        }
        composable(Routes.ACTIVE_PROGRESS) {
            ActiveProgressScreen(
                onBack = { navController.popBackStack() },
                onSkip = { navController.popBackStack() } // Mock behavior
            )
        }
    }
}