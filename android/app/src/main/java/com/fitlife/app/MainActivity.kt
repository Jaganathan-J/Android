package com.fitlife.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.fitlife.app.presentation.navigation.FitLifeNavGraph
import com.fitlife.app.ui.theme.FitLifeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitLifeTheme {
                val navController = rememberNavController()
                FitLifeNavGraph(navController = navController)
            }
        }
    }
}