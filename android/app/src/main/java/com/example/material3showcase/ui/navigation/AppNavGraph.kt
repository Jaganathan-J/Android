package com.example.material3showcase.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.material3showcase.ui.screens.avatars.AvatarsScreen
import com.example.material3showcase.ui.screens.avatars.AvatarsViewModel
import com.example.material3showcase.ui.screens.buttons.ButtonsScreen
import com.example.material3showcase.ui.screens.buttons.ButtonsViewModel
import com.example.material3showcase.ui.screens.cards.CardsScreen
import com.example.material3showcase.ui.screens.cards.CardsViewModel
import com.example.material3showcase.ui.screens.dialogs.DialogsScreen
import com.example.material3showcase.ui.screens.dialogs.DialogsViewModel
import com.example.material3showcase.ui.screens.gettingstarted.GettingStartedScreen
import com.example.material3showcase.ui.screens.gettingstarted.GettingStartedViewModel
import com.example.material3showcase.ui.screens.textfields.TextFieldsScreen
import com.example.material3showcase.ui.screens.textfields.TextFieldsViewModel
import com.example.material3showcase.ui.widgets.AppTopBar

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: Destination.GettingStarted.route

    Scaffold(
        topBar = {
            val dest = BottomNavDestinations.find { it.route == currentRoute }
            AppTopBar(
                title = dest?.label ?: "Material 3",
                canNavigateBack = navController.previousBackStackEntry != null,
                onNavigateBack = { navController.popBackStack() }
            )
        },
        bottomBar = {
            NavigationBar {
                val icons = listOf(
                    Icons.Default.Home,
                    Icons.Default.TouchApp,
                    Icons.Default.AccountCircle,
                    Icons.Default.ViewModule,
                    Icons.Default.ChatBubble,
                    Icons.Default.Article
                )
                BottomNavDestinations.forEachIndexed { index, destination ->
                    val selected = currentRoute == destination.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(icons[index], contentDescription = destination.label) },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Destination.GettingStarted.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                route = Destination.GettingStarted.route,
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300))
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300))
                },
                popEnterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300))
                },
                popExitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300))
                }
            ) {
                val vm: GettingStartedViewModel = hiltViewModel()
                GettingStartedScreen(
                    uiState = vm.uiState,
                    onNavigate = { route ->
                        navController.navigate(route)
                    }
                )
            }

            composable(Destination.Buttons.route) {
                val vm: ButtonsViewModel = hiltViewModel()
                ButtonsScreen(uiState = vm.uiState, onEvent = vm::onEvent)
            }

            composable(Destination.Avatars.route) {
                val vm: AvatarsViewModel = hiltViewModel()
                AvatarsScreen(uiState = vm.uiState, onEvent = vm::onEvent)
            }

            composable(Destination.Cards.route) {
                val vm: CardsViewModel = hiltViewModel()
                CardsScreen(uiState = vm.uiState, onEvent = vm::onEvent)
            }

            composable(Destination.Dialogs.route) {
                val vm: DialogsViewModel = hiltViewModel()
                DialogsScreen(uiState = vm.uiState, onEvent = vm::onEvent)
            }

            composable(Destination.TextFields.route) {
                val vm: TextFieldsViewModel = hiltViewModel()
                TextFieldsScreen(uiState = vm.uiState, onEvent = vm::onEvent)
            }
        }
    }
}
