package com.example.autoflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autoflow.ui.screens.auth.LoginScreen
import com.example.autoflow.ui.screens.auth.OnboardingScreen
import com.example.autoflow.ui.screens.create.CreateNameScreen
import com.example.autoflow.ui.screens.create.SelectServiceScreen
import com.example.autoflow.ui.screens.create.WorkflowPreviewScreen
import com.example.autoflow.ui.screens.home.HistoryScreen
import com.example.autoflow.ui.screens.profile.ProfileScreen

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object History : Screen("history")
    object CreateName : Screen("create_name")
    object SelectTrigger : Screen("select_trigger")
    object SelectAction : Screen("select_action")
    object Preview : Screen("preview")
    object Profile : Screen("profile")
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.Onboarding.route) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onGetStarted = { navController.navigate(Screen.Login.route) },
                onLogin = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.History.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.History.route) {
            HistoryScreen(
                onCreateNew = { navController.navigate(Screen.CreateName.route) },
                onProfile = { navController.navigate(Screen.Profile.route) }
            )
        }
        composable(Screen.CreateName.route) {
            CreateNameScreen(
                onContinue = { navController.navigate(Screen.SelectTrigger.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.SelectTrigger.route) {
            SelectServiceScreen(
                isTrigger = true,
                onSelected = { navController.navigate(Screen.SelectAction.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.SelectAction.route) {
            SelectServiceScreen(
                isTrigger = false,
                onSelected = { navController.navigate(Screen.Preview.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Preview.route) {
            WorkflowPreviewScreen(
                onSave = {
                    navController.navigate(Screen.History.route) {
                        popUpTo(Screen.History.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onLogout = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(0)
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}