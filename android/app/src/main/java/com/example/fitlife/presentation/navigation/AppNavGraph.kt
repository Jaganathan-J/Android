package com.example.fitlife.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitlife.presentation.auth.LoginScreen
import com.example.fitlife.presentation.dashboard.DashboardScreen
import com.example.fitlife.presentation.splash.SplashScreen
import com.example.fitlife.presentation.transaction.AddTransactionScreen
import com.example.fitlife.presentation.scan.TransactionsListScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigateToLogin = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onAddTransactionClick = { navController.navigate(Screen.AddTransaction.route) },
                onViewAllTransactionsClick = { navController.navigate(Screen.TransactionsList.route) }
            )
        }

        composable(Screen.AddTransaction.route) {
            AddTransactionScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.TransactionsList.route) {
            TransactionsListScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}