package com.example.automationoverflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.example.automationoverflow.presentation.theme.AutomationOverflowTheme
import com.example.automationoverflow.presentation.navigation.AppNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutomationOverflowTheme {
                AppNavGraph()
            }
        }
    }
}