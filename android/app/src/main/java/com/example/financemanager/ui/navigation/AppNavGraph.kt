package com.example.financemanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.financemanager.ui.screens.auth.LoginScreen
import com.example.financemanager.ui.screens.auth.WelcomeScreen
import com.example.financemanager.ui.screens.builder.*
import com.example.financemanager.ui.screens.dashboard.HistoryScreen
import com.example.financemanager.ui.screens.dashboard.ProfileScreen
import com.example.financemanager.ui.screens.dashboard.DashboardContainer

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "auth") {
        navigation(startDestination = "welcome", route = "auth") {
            composable("welcome") {
                WelcomeScreen(
                    onGetStarted = { navController.navigate("dashboard") }, // Skip auth for demo
                    onLogin = { navController.navigate("login") }
                )
            }
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { navController.navigate("dashboard") },
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable("dashboard") {
            DashboardContainer(
                onCreateNew = { navController.navigate("builder") },
                onLogout = {
                    navController.navigate("auth") {
                        popUpTo(0)
                    }
                }
            )
        }

        navigation(startDestination = "name_input", route = "builder") {
            composable("name_input") {
                val viewModel = hiltViewModel<WorkflowBuilderViewModel>(it)
                AutomationNameScreen(
                    viewModel = viewModel,
                    onContinue = { navController.navigate("trigger_select") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("trigger_select") {
                val entry = navController.getBackStackEntry("name_input")
                val viewModel = hiltViewModel<WorkflowBuilderViewModel>(entry)
                TriggerSelectScreen(
                    viewModel = viewModel,
                    onTriggerSelected = { navController.navigate("action_select") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("action_select") {
                val entry = navController.getBackStackEntry("name_input")
                val viewModel = hiltViewModel<WorkflowBuilderViewModel>(entry)
                ActionSelectScreen(
                    viewModel = viewModel,
                    onActionSelected = { navController.navigate("preview") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("preview") {
                val entry = navController.getBackStackEntry("name_input")
                val viewModel = hiltViewModel<WorkflowBuilderViewModel>(entry)
                WorkflowPreviewScreen(
                    viewModel = viewModel,
                    onSaveSuccess = {
                        navController.navigate("dashboard") {
                            popUpTo("dashboard") { inclusive = true }
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}