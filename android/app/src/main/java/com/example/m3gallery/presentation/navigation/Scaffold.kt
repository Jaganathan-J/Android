package com.example.m3gallery.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3Scaffold(
    navController: NavController,
    currentRoute: String,
    drawerContent: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController = navController)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = currentTitleForRoute(currentRoute)) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                            Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Settings")
                        }
                    }
                )
            },
            bottomBar = bottomBar
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

@Composable
fun DrawerContent(navController: NavController) {
    val items = listOf(Screen.Home, Screen.Buttons, Screen.Cards, Screen.Dialogs, Screen.Settings)
    androidx.compose.material3.ModalDrawerSheet {
        Text(
            text = "M3 Components",
            modifier = Modifier.padding(16.dp)
        )
        items.forEach { screen ->
            NavigationDrawerItem(
                label = { Text(screen.title) },
                selected = false,
                onClick = { navController.navigate(screen.route) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    androidx.compose.material3.NavigationBar {
        val items = listOf(Screen.Home, Screen.Buttons, Screen.Cards, Screen.Dialogs)
        items.forEach { screen ->
            val selected = currentRoute == screen.route
            androidx.compose.material3.NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(screen.route) },
                icon = {
                    Text(text = screen.title.first().toString())
                },
                label = { Text(text = screen.title) }
            )
        }
    }
}

private fun currentTitleForRoute(route: String): String = when (route) {
    Screen.Home.route -> Screen.Home.title
    Screen.Buttons.route -> Screen.Buttons.title
    Screen.Cards.route -> Screen.Cards.title
    Screen.Dialogs.route -> Screen.Dialogs.title
    Screen.Settings.route -> Screen.Settings.title
    else -> "M3 Gallery"
}
