package com.automationoverflow.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.automationoverflow.app.presentation.auth.LoginScreen
import com.automationoverflow.app.presentation.history.HistoryScreen
import com.automationoverflow.app.presentation.onboarding.OnboardingScreen
import com.automationoverflow.app.presentation.profile.ProfileScreen
import com.automationoverflow.app.presentation.wizard.*

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Wizard : Screen("wizard_graph")
    object WizardName : Screen("wizard_name")
    object WizardTrigger : Screen("wizard_trigger")
    object WizardAction : Screen("wizard_action")
    object WizardPreview : Screen("wizard_preview")
    object History : Screen("history")
    object Profile : Screen("profile")
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Onboarding.route) {
        
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onGetStarted = { navController.navigate(Screen.Login.route) },
                onLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onLoginSuccess = { 
                    // Go to wizard for demo flow, usually would be Dashboard
                    navController.navigate(Screen.Wizard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // Wizard Flow
        navigation(startDestination = Screen.WizardName.route, route = Screen.Wizard.route) {
            composable(Screen.WizardName.route) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry(Screen.Wizard.route)
                }
                val viewModel: WizardViewModel = hiltViewModel(parentEntry)
                
                NameScreen(
                    viewModel = viewModel,
                    onBack = { 
                        // Quick hack to go to history/profile from here for demo
                        navController.navigate(Screen.History.route)
                    },
                    onNext = { navController.navigate(Screen.WizardTrigger.route) }
                )
            }
            composable(Screen.WizardTrigger.route) {
                 val parentEntry = remember(it) {
                    navController.getBackStackEntry(Screen.Wizard.route)
                }
                val viewModel: WizardViewModel = hiltViewModel(parentEntry)

                TriggerSelectScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onNext = { navController.navigate(Screen.WizardAction.route) }
                )
            }
            composable(Screen.WizardAction.route) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry(Screen.Wizard.route)
                }
                val viewModel: WizardViewModel = hiltViewModel(parentEntry)

                ActionSelectScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onNext = { navController.navigate(Screen.WizardPreview.route) }
                )
            }
            composable(Screen.WizardPreview.route) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry(Screen.Wizard.route)
                }
                val viewModel: WizardViewModel = hiltViewModel(parentEntry)

                PreviewScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onSave = {
                        navController.navigate(Screen.History.route) {
                            popUpTo(Screen.Wizard.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(Screen.History.route) {
            HistoryScreen(
                onBack = { 
                    navController.navigate(Screen.Profile.route) 
                } // Navigating to profile via back for demo access
            )
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}