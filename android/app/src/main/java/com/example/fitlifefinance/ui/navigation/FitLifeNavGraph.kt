package com.example.fitlifefinance.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitlifefinance.ui.screens.accounts.AccountsScreen
import com.example.fitlifefinance.ui.screens.addexpense.AddExpenseScreen
import com.example.fitlifefinance.ui.screens.analytics.AnalyticsScreen
import com.example.fitlifefinance.ui.screens.auth.SignInScreen
import com.example.fitlifefinance.ui.screens.budget.BudgetScreen
import com.example.fitlifefinance.ui.screens.home.HomeScreen
import com.example.fitlifefinance.ui.screens.notifications.NotificationsScreen
import com.example.fitlifefinance.ui.screens.onboarding.OnboardingScreen
import com.example.fitlifefinance.ui.screens.profile.ProfileScreen
import com.example.fitlifefinance.ui.screens.transactions.TransactionsScreen

object Routes {
    const val ONBOARDING = "onboarding"
    const val SIGN_IN = "sign_in"
    const val HOME = "home"
    const val ANALYTICS = "analytics"
    const val ACCOUNTS = "cards"
    const val BUDGET = "budget"
    const val PROFILE = "profile"
    const val TRANSACTIONS = "transactions"
    const val ADD_EXPENSE = "add_expense"
    const val NOTIFICATIONS = "notifications"
}

@Composable
fun FitLifeNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.ONBOARDING) {
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onGetStarted = { navController.navigate(Routes.SIGN_IN) },
                onSignIn = { navController.navigate(Routes.SIGN_IN) }
            )
        }
        composable(Routes.SIGN_IN) {
            SignInScreen(
                onBack = { navController.popBackStack() },
                onSignInSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.HOME) {
            HomeScreen(
                navController = navController,
                onNavigateToNotifications = { navController.navigate(Routes.NOTIFICATIONS) },
                onNavigateToTransactions = { navController.navigate(Routes.TRANSACTIONS) },
                onNavigateToAddExpense = { navController.navigate(Routes.ADD_EXPENSE) }
            )
        }
        composable(Routes.ANALYTICS) {
            AnalyticsScreen(navController = navController)
        }
        composable(Routes.ACCOUNTS) {
            AccountsScreen(navController = navController)
        }
        composable(Routes.BUDGET) {
            BudgetScreen(navController = navController)
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                navController = navController,
                onSignOut = {
                    navController.navigate(Routes.ONBOARDING) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.TRANSACTIONS) {
            TransactionsScreen(navController = navController)
        }
        composable(Routes.ADD_EXPENSE) {
            AddExpenseScreen(navController = navController)
        }
        composable(Routes.NOTIFICATIONS) {
            NotificationsScreen(navController = navController)
        }
    }
}