package com.example.fitlife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitlife.ui.screens.*
import com.example.fitlife.ui.theme.FitLifeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitLifeTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("history") { HistoryScreen(navController) }
        
        // Wizard Flow
        composable("create_wizard") {
             val wizardViewModel: WizardViewModel = hiltViewModel()
             CreateAutomationScreen(navController, wizardViewModel)
        }
        composable("trigger_select") {
             // Ideally, scoped ViewModel to a nav graph, but usage here implies sharing
             val backStackEntry = remember(navController.currentBackStackEntry) { 
                 navController.getBackStackEntry("create_wizard") 
             }
             val wizardViewModel: WizardViewModel = hiltViewModel(backStackEntry)
             TriggerSelectScreen(navController, wizardViewModel)
        }
        composable("action_select") {
             val backStackEntry = remember(navController.currentBackStackEntry) { 
                 navController.getBackStackEntry("create_wizard") 
             }
             val wizardViewModel: WizardViewModel = hiltViewModel(backStackEntry)
             ActionSelectScreen(navController, wizardViewModel)
        }
        composable("preview") {
             val backStackEntry = remember(navController.currentBackStackEntry) { 
                 navController.getBackStackEntry("create_wizard") 
             }
             val wizardViewModel: WizardViewModel = hiltViewModel(backStackEntry)
             PreviewScreen(navController, wizardViewModel)
        }
    }
}