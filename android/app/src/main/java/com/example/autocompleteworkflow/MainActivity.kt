package com.example.autocompleteworkflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.example.autocompleteworkflow.presentation.navigation.AppNavGraph
import com.example.autocompleteworkflow.presentation.theme.AutocompleteWorkflowTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutocompleteWorkflowTheme {
                AppNavGraph()
            }
        }
    }
}