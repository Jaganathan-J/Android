package com.example.m3gallery.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.TouchApp
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.m3gallery.presentation.screens.buttons.ButtonsScreen
import com.example.m3gallery.presentation.screens.cards.CardsScreen
import com.example.m3gallery.presentation.screens.dialogs.DialogsScreen
import com.example.m3gallery.presentation.screens.home.HomeScreen
import com.example.m3gallery.presentation.screens.settings.SettingsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3GalleryApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val bottomDestinations = listOf(
        BottomDestination("Home", Screen.Home.route, Icons.Default.Home),
        BottomDestination("Components", Screen.Buttons.route, Icons.Outlined.TouchApp),
        BottomDestination("Settings", Screen.Settings.route, Icons.Default.Settings)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentRoute = currentRoute(navController),
                onDestinationSelected = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "M3 Components Gallery") },
                    navigationIcon = {
                        androidx.compose.material3.IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isOpen) drawerState.close() else drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Outlined.Dashboard, contentDescription = "Open navigation drawer")
                        }
                    }
                )
            },
            bottomBar = {
                BottomBar(
                    navController = navController,
                    destinations = bottomDestinations
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(onNavigate = { route -> navController.navigate(route) })
                }
                composable(Screen.Buttons.route) {
                    ButtonsScreen()
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
    }
}

@Composable
private fun DrawerContent(
    currentRoute: String?,
    onDestinationSelected: (String) -> Unit
) {
    val items = listOf(
        DrawerDestination("Home", Screen.Home.route),
        DrawerDestination("Buttons", Screen.Buttons.route),
        DrawerDestination("Cards", Screen.Cards.route),
        DrawerDestination("Dialogs", Screen.Dialogs.route),
        DrawerDestination("Settings", Screen.Settings.route)
    )

    androidx.compose.material3.ModalDrawerSheet {
        items.forEach { item ->
            NavigationDrawerItem(
                label = { Text(text = item.label) },
                selected = currentRoute == item.route,
                onClick = { onDestinationSelected(item.route) },
                modifier = Modifier,
                icon = null,
                colors = NavigationDrawerItemDefaults.colors()
            )
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    destinations: List<BottomDestination>
) {
    val currentRoute = currentRoute(navController)
    NavigationBar {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(destination.icon, contentDescription = destination.label) },
                label = { Text(destination.label) }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val backStackEntry by navController.currentBackStackEntryAsState()
    return backStackEntry?.destination?.route
}

private data class BottomDestination(
    val label: String,
    val route: String,
    val icon: ImageVector
)

private data class DrawerDestination(
    val label: String,
    val route: String
)
