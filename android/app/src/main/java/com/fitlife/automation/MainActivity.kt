package com.fitlife.automation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fitlife.automation.domain.repository.AuthRepository
import com.fitlife.automation.ui.screens.*
import com.fitlife.automation.ui.theme.FitLifeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject lateinit var authRepo: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitLifeTheme {
                FitLifeNavGraph(authRepo)
            }
        }
    }
}

@Composable
fun FitLifeNavGraph(authRepo: AuthRepository) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
        composable("profile") { ProfileScreen(navController, authRepo) }
        
        // Workflow Wizard Graph
        // sharing viewModel for wizard scope (simplified for this output, normally nested graph)
        composable("create_init") {
            val viewModel = hiltViewModel<WorkflowWizardViewModel>(it)
            InitiateScreen(navController, viewModel)
        }
        composable("create_trigger") {
             // In real apps, use backStackEntry to share viewmodel or NavGraph builder
             val backStackEntry = remember(it) { navController.getBackStackEntry("create_init") }
             val viewModel = hiltViewModel<WorkflowWizardViewModel>(backStackEntry)
             TriggerSelectScreen(navController, viewModel)
        }
        composable("create_action") {
             val backStackEntry = remember(it) { navController.getBackStackEntry("create_init") }
             val viewModel = hiltViewModel<WorkflowWizardViewModel>(backStackEntry)
             ActionSelectScreen(navController, viewModel)
        }
        composable("create_preview") {
             val backStackEntry = remember(it) { navController.getBackStackEntry("create_init") }
             val viewModel = hiltViewModel<WorkflowWizardViewModel>(backStackEntry)
             PreviewScreen(navController, viewModel)
        }
    }
}