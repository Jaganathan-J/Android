package com.example.autocompleteworkflow.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autocompleteworkflow.presentation.screens.welcome.WelcomeScreen
import com.example.autocompleteworkflow.presentation.screens.login.LoginScreen
import com.example.autocompleteworkflow.presentation.screens.dashboard.HistoryScreen
import com.example.autocompleteworkflow.presentation.screens.create.CreateWorkflowFlow
import com.example.autocompleteworkflow.presentation.screens.profile.ProfileScreen

object Routes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val DASHBOARD = "dashboard"
    const val CREATE_FLOW = "create_flow"
    const val PROFILE = "profile"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.WELCOME) {
        composable(Routes.WELCOME) {
            WelcomeScreen(
                onGetStarted = { navController.navigate(Routes.LOGIN) },
                onLogin = { navController.navigate(Routes.LOGIN) }
            )
        }
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.WELCOME) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.DASHBOARD) {
            HistoryScreen(
                onCreateClick = { navController.navigate(Routes.CREATE_FLOW) },
                onProfileClick = { navController.navigate(Routes.PROFILE) }
            )
        }
        composable(Routes.CREATE_FLOW) {
            CreateWorkflowFlow(
                onFinished = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Routes.WELCOME) {
                        popUpTo(Routes.DASHBOARD) { inclusive = true }
                    }
                }
            )
        }
    }
}