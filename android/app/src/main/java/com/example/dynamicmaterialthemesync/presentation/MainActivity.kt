package com.example.dynamicmaterialthemesync.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.example.dynamicmaterialthemesync.presentation.navigation.AppNavHost
import com.example.dynamicmaterialthemesync.ui.theme.DynamicTheme
import com.example.dynamicmaterialthemesync.ui.theme.LocalDynamicThemeController
import com.example.dynamicmaterialthemesync.ui.theme.rememberDynamicThemeController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val controller = rememberDynamicThemeController()
            val themeState by controller.themeState.collectAsState()

            DynamicTheme(themeState = themeState) { colorScheme ->
                window.statusBarColor = colorScheme.background.toArgb()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    window.navigationBarColor = colorScheme.background.toArgb()
                }

                CompositionLocalProvider(LocalDynamicThemeController provides controller) {
                    AppNavHost()
                }
            }
        }
    }
}