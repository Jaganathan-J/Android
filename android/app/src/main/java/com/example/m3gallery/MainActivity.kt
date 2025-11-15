package com.example.m3gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import com.example.m3gallery.presentation.navigation.M3GalleryApp
import com.example.m3gallery.ui.theme.M3GalleryTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            M3GalleryTheme {
                Surface(modifier = Modifier) {
                    M3GalleryApp()
                }
            }
        }
    }
}
