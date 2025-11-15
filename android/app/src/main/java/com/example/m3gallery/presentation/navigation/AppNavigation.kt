package com.example.m3gallery.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.m3gallery.presentation.screens.buttons.ButtonsScreen
import com.example.m3gallery.presentation.screens.cards.CardsScreen
import com.example.m3gallery.presentation.screens.dialogs.DialogsScreen
import com.example.m3gallery.presentation.screens.home.HomeScreen
import com.example.m3gallery.presentation.screens.settings.SettingsScreen
import com.example.m3gallery.presentation.viewmodel.ButtonsViewModel
import com.example.m3gallery.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(
            route = Screen.Home.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            }
        ) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                state = viewModel.state,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.Buttons.route) {
            val viewModel: ButtonsViewModel = hiltViewModel()
            ButtonsScreen(viewModel = viewModel)
        }

        composable(Screen.Cards.route) {
            CardsScreen()
        }

        composable(Screen.Dialogs.route) {
            DialogsScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
