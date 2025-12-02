package com.example.fitlife.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object AddTransaction : Screen("add_transaction")
    object TransactionsList : Screen("transactions_list")
}