package com.example.m3gallery.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.m3gallery.core.UiEvent
import com.example.m3gallery.presentation.screens.buttons.ButtonsScreen
import com.example.m3gallery.presentation.screens.cards.CardsScreen
import com.example.m3gallery.presentation.screens.dialogs.DialogsScreen
import com.example.m3gallery.presentation.screens.home.HomeScreen
import com.example.m3gallery.presentation.screens.settings.SettingsScreen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

private data class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector,
    val label: String
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun M3NavGraph() {
    val navController: NavHostController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val uiEventFlow = remember { MutableSharedFlow<UiEvent>() }

    LaunchedEffect(Unit) {
        uiEventFlow.asSharedFlow().collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                }
                is UiEvent.NavigateBack -> navController.popBackStack()
                is UiEvent.NavigateTo -> navController.navigate(event.route)
            }
        }
    }

    val bottomItems = listOf(
        BottomNavItem(Screen.Home, Icons.Default.Home, "Home"),
        BottomNavItem(Screen.Settings, Icons.Default.Settings, "Settings")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            androidx.compose.material3.ModalDrawerSheet {
                Text(
                    text = "M3 Components",
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Buttons") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.Buttons.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Cards") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.Cards.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Dialogs") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.Dialogs.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                val backStackEntry = navController.currentBackStackEntryAsState().value
                val currentRoute = backStackEntry?.destination?.route ?: Screen.Home.route
                TopAppBar(
                    title = { Text(getTitleForRoute(currentRoute)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isOpen) drawerState.close() else drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate(Screen.Buttons.route) }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Buttons Shortcut"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    val backStackEntry = navController.currentBackStackEntryAsState().value
                    val currentRoute = backStackEntry?.destination?.route
                    bottomItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.screen.route,
                            onClick = {
                                if (currentRoute != item.screen.route) {
                                    navController.navigate(item.screen.route) {
                                        popUpTo(Screen.Home.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(imageVector = item.icon, contentDescription = item.label)
                            },
                            label = { Text(item.label) }
                        )
                    }
                }
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues: PaddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                addHome(navController, uiEventFlow)
                addButtons(navController, uiEventFlow)
                addCards(navController, uiEventFlow)
                addDialogs(navController, uiEventFlow)
                addSettings(navController)
            }
        }
    }
}

private fun NavGraphBuilder.addHome(
    navController: NavHostController,
    uiEventFlow: MutableSharedFlow<UiEvent>
) {
    composable(Screen.Home.route) {
        HomeScreen(
            onNavigateToButtons = { navController.navigate(Screen.Buttons.route) },
            onNavigateToCards = { navController.navigate(Screen.Cards.route) },
            onNavigateToDialogs = { navController.navigate(Screen.Dialogs.route) }
        )
    }
}

private fun NavGraphBuilder.addButtons(
    navController: NavHostController,
    uiEventFlow: MutableSharedFlow<UiEvent>
) {
    composable(Screen.Buttons.route) {
        val viewModel: com.example.m3gallery.presentation.viewmodels.ButtonsViewModel = hiltViewModel()
        ButtonsScreen(
            viewModel = viewModel,
            onBack = { navController.popBackStack() },
            onShowSnackbar = { message, action ->
                val event = UiEvent.ShowSnackbar(message, action)
                val scope = rememberCoroutineScopeForGraph()
                scope.launch { uiEventFlow.emit(event) }
            }
        )
    }
}

private fun NavGraphBuilder.addCards(
    navController: NavHostController,
    uiEventFlow: MutableSharedFlow<UiEvent>
) {
    composable(Screen.Cards.route) {
        CardsScreen(onBack = { navController.popBackStack() })
    }
}

private fun NavGraphBuilder.addDialogs(
    navController: NavHostController,
    uiEventFlow: MutableSharedFlow<UiEvent>
) {
    composable(Screen.Dialogs.route) {
        DialogsScreen(onBack = { navController.popBackStack() })
    }
}

private fun NavGraphBuilder.addSettings(
    navController: NavHostController
) {
    composable(Screen.Settings.route) {
        SettingsScreen(onBack = { navController.popBackStack() })
    }
}

@Composable
private fun rememberCoroutineScopeForGraph() = rememberCoroutineScope()

private fun getTitleForRoute(route: String): String = when (route) {
    Screen.Home.route -> "Home"
    Screen.Buttons.route -> "Buttons Demo"
    Screen.Cards.route -> "Cards Demo"
    Screen.Dialogs.route -> "Dialogs Demo"
    Screen.Settings.route -> "Settings"
    else -> "M3 Gallery"
}
