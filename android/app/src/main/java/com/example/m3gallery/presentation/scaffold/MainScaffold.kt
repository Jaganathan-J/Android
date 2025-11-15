package com.example.m3gallery.presentation.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.material3.rememberSnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.m3gallery.R
import com.example.m3gallery.presentation.ScaffoldEvent
import com.example.m3gallery.presentation.ScaffoldUiState
import com.example.m3gallery.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    uiState: ScaffoldUiState,
    onEvent: (ScaffoldEvent) -> Unit,
    content: @Composable (innerPadding: androidx.compose.foundation.layout.PaddingValues, SnackbarHostState, NavHostController) -> Unit
) {
    val navController = rememberNavController()
    val snackbarHostState = rememberSnackbarHostState()
    val drawerState = rememberDrawerState(
        initialValue = if (uiState.isDrawerOpen) DrawerValue.Open else DrawerValue.Closed
    )

    LaunchedEffect(drawerState.currentValue) {
        if (drawerState.currentValue == DrawerValue.Closed && uiState.isDrawerOpen) {
            onEvent(ScaffoldEvent.OnDrawerClose)
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentDestination = currentDestination,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                    onEvent(ScaffoldEvent.OnDrawerClose)
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = { onEvent(ScaffoldEvent.OnMenuClick) }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                BottomBar(
                    currentDestination = currentDestination,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        ) { innerPadding ->
            content(innerPadding, snackbarHostState, navController)
        }
    }
}

@Composable
private fun DrawerContent(
    currentDestination: NavDestination?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        Screen.Home,
        Screen.Buttons,
        Screen.Cards,
        Screen.Dialogs,
        Screen.Settings
    )
    androidx.compose.material3.ModalDrawerSheet {
        Text(
            text = "M3 Component Gallery",
            modifier = Modifier.padding(16.dp)
        )
        items.forEach { screen ->
            val selected = currentDestination?.route == screen.route
            NavigationDrawerItem(
                label = { Text(text = screen.route.replaceFirstChar { it.uppercase() }) },
                selected = selected,
                onClick = { onNavigate(screen.route) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}

@Composable
private fun BottomBar(
    currentDestination: NavDestination?,
    onNavigate: (String) -> Unit
) {
    val destinations = remember {
        listOf(Screen.Home, Screen.Buttons, Screen.Cards, Screen.Dialogs)
    }
    NavigationBar {
        destinations.forEach { screen ->
            val selected = currentDestination?.route == screen.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(screen.route) },
                icon = {},
                label = { Text(text = screen.route.replaceFirstChar { it.uppercase() }) }
            )
        }
    }
}
