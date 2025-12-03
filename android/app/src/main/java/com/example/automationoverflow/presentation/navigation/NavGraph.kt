package com.example.automationoverflow.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.automationoverflow.presentation.screens.history.HistoryScreen
import com.example.automationoverflow.presentation.screens.login.LoginScreen
import com.example.automationoverflow.presentation.screens.onboarding.OnboardingScreen
import com.example.automationoverflow.presentation.screens.profile.ProfileScreen
import com.example.automationoverflow.presentation.screens.wizard.*

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "onboarding") {
        
        composable("onboarding") {
            OnboardingScreen(
                onGetStarted = { navController.navigate("login") },
                onLogin = { navController.navigate("login") }
            )
        }

        composable("login") {
            LoginScreen(
                onLoginSuccess = { 
                    navController.navigate("history") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("history") {
            HistoryScreen(
                onCreateNew = { navController.navigate("wizard") },
                onProfile = { navController.navigate("profile") }
            )
        }

        composable("profile") {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = { 
                    navController.navigate("onboarding") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        navigation(startDestination = "wizard_name", route = "wizard") {
            composable("wizard_name") {
                val viewModel = hiltViewModel<WizardViewModel>(it)
                WizardNameScreen(
                    viewModel = viewModel,
                    onNext = { navController.navigate("wizard_trigger") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("wizard_trigger") {
                // Use parent entry to share ViewModel scoped to graph if desired,
                // but simpler for now to pass data or use shared VM logic pattern.
                // Here we will just assume simple navigation.
                val viewModel = hiltViewModel<WizardViewModel>(navController.getBackStackEntry("wizard_name"))
                WizardTriggerScreen(
                    viewModel = viewModel,
                    onNext = { navController.navigate("wizard_action") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("wizard_action") {
                val viewModel = hiltViewModel<WizardViewModel>(navController.getBackStackEntry("wizard_name"))
                WizardActionScreen(
                    viewModel = viewModel,
                    onNext = { navController.navigate("wizard_preview") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("wizard_preview") {
                val viewModel = hiltViewModel<WizardViewModel>(navController.getBackStackEntry("wizard_name"))
                WizardPreviewScreen(
                    viewModel = viewModel,
                    onSaveSuccess = { 
                         navController.navigate("history") {
                             popUpTo("history") { inclusive = true }
                         }
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}