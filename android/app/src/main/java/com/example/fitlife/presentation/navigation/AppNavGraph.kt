package com.example.fitlife.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitlife.presentation.screens.welcome.WelcomeScreen
import com.example.fitlife.presentation.screens.auth.LoginScreen
import com.example.fitlife.presentation.screens.dashboard.DashboardScreen
import com.example.fitlife.presentation.screens.create.CreateNameScreen
import com.example.fitlife.presentation.screens.create.ChooseTriggerScreen
import com.example.fitlife.presentation.screens.create.ChooseActionScreen
import com.example.fitlife.presentation.screens.create.PreviewScreen
import com.example.fitlife.presentation.screens.history.HistoryScreen
import com.example.fitlife.presentation.screens.profile.ProfileScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onGetStarted = { navController.navigate("login") },
                onLogin = { navController.navigate("login") }
            )
        }
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("welcome") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                onCreateClick = { navController.navigate("create_name") },
                onHistoryClick = { navController.navigate("history") },
                onProfileClick = { navController.navigate("profile") }
            )
        }
        
        // Create Flow
        composable("create_name") {
            CreateNameScreen(
                onBack = { navController.popBackStack() },
                onContinue = { name -> navController.navigate("choose_trigger/$name") }
            )
        }
        composable(
            "choose_trigger/{workflowName}",
            arguments = listOf(navArgument("workflowName") { type = NavType.StringType })
        ) {
            val name = it.arguments?.getString("workflowName") ?: ""
            ChooseTriggerScreen(
                onBack = { navController.popBackStack() },
                onTriggerSelected = { tId -> navController.navigate("choose_action/$name/$tId") }
            )
        }
        composable(
            "choose_action/{workflowName}/{triggerId}",
             arguments = listOf(
                 navArgument("workflowName") { type = NavType.StringType },
                 navArgument("triggerId") { type = NavType.StringType }
             )
        ) {
            val name = it.arguments?.getString("workflowName") ?: ""
            val triggerId = it.arguments?.getString("triggerId") ?: ""
            ChooseActionScreen(
                onBack = { navController.popBackStack() },
                onActionSelected = { aId -> navController.navigate("preview/$name/$triggerId/$aId") }
            )
        }
        composable(
             "preview/{workflowName}/{triggerId}/{actionId}",
             arguments = listOf(
                 navArgument("workflowName") { type = NavType.StringType },
                 navArgument("triggerId") { type = NavType.StringType },
                 navArgument("actionId") { type = NavType.StringType }
             )
        ) {
            val name = it.arguments?.getString("workflowName") ?: ""
            val tId = it.arguments?.getString("triggerId") ?: ""
            val aId = it.arguments?.getString("actionId") ?: ""
            PreviewScreen(
             workflowName = name,
             triggerId = tId,
             actionId = aId,
             onBack = { navController.popBackStack() },
             onSave = {
                 navController.navigate("dashboard") { popUpTo("dashboard") { inclusive = true } }
             }
            )
        }

        composable("history") {
            HistoryScreen(onBack = { navController.popBackStack() })
        }
        composable("profile") {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate("welcome") {
                        popUpTo(0) // clear backstack
                    }
                }
            )
        }
    }
}