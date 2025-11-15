package com.example.material3showcase.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.material3showcase.presentation.screen.AvatarsScreen
import com.example.material3showcase.presentation.screen.ButtonsScreen
import com.example.material3showcase.presentation.screen.CardsScreen
import com.example.material3showcase.presentation.screen.DialogsScreen
import com.example.material3showcase.presentation.screen.GettingStartedScreen
import com.example.material3showcase.presentation.screen.TextFieldsScreen

sealed class Screen(val route: String, val label: String) {
    data object GettingStarted : Screen("gettingStarted", "Getting Started")
    data object Avatars : Screen("avatars", "Avatars")
    data object Buttons : Screen("buttons", "Buttons")
    data object Cards : Screen("cards", "Cards")
    data object Dialogs : Screen("dialogs", "Dialogs")
    data object TextFields : Screen("textFields", "Text Fields")
}

@Composable
fun rememberAppNavController(): NavHostController = rememberNavController()

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.GettingStarted.route,
        modifier = modifier.padding(innerPadding)
    ) {
        composable(
            route = Screen.GettingStarted.route,
            enterTransition = {
                fadeIn() + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                fadeOut() + slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }
        ) {
            GettingStartedScreen(navController = navController)
        }

        composable(Screen.Avatars.route) {
            AvatarsScreen()
        }
        composable(Screen.Buttons.route) {
            ButtonsScreen(snackbarHostState = snackbarHostState)
        }
        composable(Screen.Cards.route) {
            CardsScreen()
        }
        composable(Screen.Dialogs.route) {
            DialogsScreen()
        }
        composable(Screen.TextFields.route) {
            TextFieldsScreen()
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        Screen.GettingStarted,
        Screen.Avatars,
        Screen.Buttons,
        Screen.Cards,
        Screen.Dialogs,
        Screen.TextFields
    )
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    androidx.compose.material3.NavigationBar {
        items.forEach { screen ->
            val selected = currentRoute == screen.route
            androidx.compose.material3.NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {},
                label = { androidx.compose.material3.Text(screen.label) }
            )
        }
    }
}
