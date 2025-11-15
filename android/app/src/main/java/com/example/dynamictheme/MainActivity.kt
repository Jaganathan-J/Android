package com.example.dynamictheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import com.example.dynamictheme.domain.model.ThemeModel
import com.example.dynamictheme.presentation.theme.AppTheme
import com.example.dynamictheme.presentation.theme.navigation.NavGraph
import com.example.dynamictheme.presentation.theme.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val themeState by themeViewModel.themeModel.collectAsState()
            val snackbarHostState = remember { SnackbarHostState() }
            val uiEvents = themeViewModel.uiEvents

            LaunchedEffect(Unit) {
                uiEvents.collect { event ->
                    when (event) {
                        is com.example.dynamictheme.presentation.theme.state.ThemeUiEvent.ShowMessage -> {
                            snackbarHostState.showSnackbar(event.message)
                        }
                    }
                }
            }

            AppTheme(themeModel = themeState ?: ThemeModel.default()) {
                Surface {
                    Crossfade(targetState = themeState) { _ ->
                        NavGraph(snackbarHost = { SnackbarHost(hostState = snackbarHostState) })
                    }
                }
            }
        }
    }
}