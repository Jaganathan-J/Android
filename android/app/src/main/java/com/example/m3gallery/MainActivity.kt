package com.example.m3gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.m3gallery.presentation.navigation.BottomNavBar
import com.example.m3gallery.presentation.navigation.DrawerContent
import com.example.m3gallery.presentation.navigation.M3NavHost
import com.example.m3gallery.presentation.navigation.Screen
import com.example.m3gallery.presentation.ui.theme.M3GalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3GalleryApp()
        }
    }
}

@Composable
fun M3GalleryApp() {
    M3GalleryTheme {
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route ?: Screen.Home.route

        Surface {
            com.example.m3gallery.presentation.navigation.M3Scaffold(
                navController = navController,
                currentRoute = currentRoute,
                drawerContent = { DrawerContent(navController = navController) },
                bottomBar = {
                    BottomNavBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            if (route != currentRoute) {
                                navController.navigate(route) {
                                    popUpTo(Screen.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            ) { innerPadding ->
                M3NavHost(navController = navController, innerPadding = innerPadding)
            }
        }
    }
}
