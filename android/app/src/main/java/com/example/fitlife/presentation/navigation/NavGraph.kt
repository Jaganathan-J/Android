package com.example.fitlife.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitlife.presentation.screens.accounts.AccountsScreen
import com.example.fitlife.presentation.screens.addexpense.AddExpenseScreen
import com.example.fitlife.presentation.screens.budget.BudgetScreen
import com.example.fitlife.presentation.screens.dashboard.DashboardScreen
import com.example.fitlife.presentation.screens.settings.SettingsScreen
import com.example.fitlife.presentation.screens.splash.SplashScreen
import com.example.fitlife.presentation.screens.transactions.TransactionsScreen

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Dashboard : Screen("dashboard")
    data object Transactions : Screen("transactions")
    data object AddExpense : Screen("add_expense")
    data object Accounts : Screen("accounts")
    data object Budget : Screen("budget")
    data object Settings : Screen("settings")
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(onGetStarted = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToTransactions = { navController.navigate(Screen.Transactions.route) },
                onNavigateToAddExpense = { navController.navigate(Screen.AddExpense.route) },
                onNavigateToAccounts = { navController.navigate(Screen.Accounts.route) },
                onNavigateToBudget = { navController.navigate(Screen.Budget.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.Transactions.route) {
            TransactionsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AddExpense.route) {
            AddExpenseScreen(
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }
        composable(Screen.Accounts.route) {
            AccountsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Budget.route) {
            BudgetScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Screen.Splash.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}