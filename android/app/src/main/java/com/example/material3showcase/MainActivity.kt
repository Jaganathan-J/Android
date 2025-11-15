package com.example.material3showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import dagger.hilt.android.AndroidEntryPoint
import com.example.material3showcase.ui.navigation.AppNavHost
import com.example.material3showcase.ui.theme.AppTheme
import com.example.material3showcase.ui.theme.LocalSnackbarHostState
import androidx.compose.material3.SnackbarHostState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = isSystemInDarkTheme()
            val snackbarHostState = SnackbarHostState()

            AppTheme(darkTheme = darkTheme) {
                CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
                    Surface {
                        AppNavHost()
                    }
                }
            }
        }
    }
}
