package com.example.dynamicthemesync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dynamicthemesync.domain.model.ThemeModel
import com.example.dynamicthemesync.domain.usecase.ObserveThemeUseCase
import com.example.dynamicthemesync.presentation.theme.AppTheme
import com.example.dynamicthemesync.presentation.theme.settings.IconStylePickerSheet
import com.example.dynamicthemesync.presentation.theme.settings.ThemeSettingsScreen
import com.example.dynamicthemesync.presentation.theme.settings.ThemeViewModel
import com.example.dynamicthemesync.presentation.theme.typography.TypographyPickerScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var observeThemeUseCase: ObserveThemeUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeModelState = observeThemeUseCase().collectAsState(initial = ThemeModel.default())
            val navController = rememberNavController()

            Crossfade(targetState = themeModelState.value, label = "app-theme-crossfade") { themeModel ->
                AppTheme(themeModel = themeModel) {
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}

@Composable
private fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "settings/theme") {
        composable("settings/theme") {
            val vm: ThemeViewModel = hiltViewModel()
            ThemeSettingsScreen(
                viewModel = vm,
                onNavigateToTypography = { navController.navigate("settings/theme/typography") },
                onNavigateToIconPicker = { navController.navigate("settings/theme/icons") }
            )
        }
        composable("settings/theme/typography") {
            val vm: ThemeViewModel = hiltViewModel()
            TypographyPickerScreen(
                uiState = vm.uiState.collectAsState().value,
                onFontSelected = { vm.onSelectTypography(it) },
                onApply = { vm.onApplyChanges(); navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }
        composable("settings/theme/icons") {
            val vm: ThemeViewModel = hiltViewModel()
            IconStylePickerSheet(
                uiState = vm.uiState.collectAsState().value,
                onStyleSelected = { vm.onSelectIconStyle(it) },
                onAxesChanged = { weight, grade, opsz -> vm.onUpdateIconAxes(weight, grade, opsz) },
                onApply = { vm.onApplyChanges(); navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }
        composable(
            route = "settings/theme/deeplink/{action}",
            arguments = listOf(navArgument("action") { type = NavType.StringType })
        ) { backStackEntry ->
            val action = backStackEntry.arguments?.getString("action")
            val vm: ThemeViewModel = hiltViewModel()
            if (action == "sync") {
                vm.onRequestSync()
            }
            ThemeSettingsScreen(
                viewModel = vm,
                onNavigateToTypography = { navController.navigate("settings/theme/typography") },
                onNavigateToIconPicker = { navController.navigate("settings/theme/icons") }
            )
        }
    }
}