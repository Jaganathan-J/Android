package com.example.m3gallery.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m3gallery.core.UiEvent
import com.example.m3gallery.presentation.navigation.MaterialNavHost
import com.example.m3gallery.presentation.scaffold.MainScaffold
import com.example.m3gallery.ui.theme.MaterialShowcaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialShowcaseTheme {
                Surface {
                    MaterialRoot()
                }
            }
        }
    }
}

@Composable
fun MaterialRoot() {
    val scaffoldViewModel: ScaffoldViewModel = hiltViewModel()
    val scaffoldState by scaffoldViewModel.uiState.collectAsState()

    MainScaffold(
        uiState = scaffoldState,
        onEvent = scaffoldViewModel::onEvent
    ) { innerPadding, snackbarHostState, navController ->
        LaunchedEffect(Unit) {
            scaffoldViewModel.uiEvents.collect { event ->
                when (event) {
                    is UiEvent.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.actionLabel
                        )
                    }

                    UiEvent.NavigateBack -> {
                        navController.popBackStack()
                    }
                }
            }
        }

        MaterialNavHost(
            navController = navController,
            innerPadding = innerPadding
        )
    }
}
