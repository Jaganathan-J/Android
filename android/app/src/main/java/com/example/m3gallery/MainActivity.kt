package com.example.m3gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.m3gallery.presentation.navigation.M3NavGraph
import com.example.m3gallery.ui.theme.M3GalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            M3GalleryAppRoot()
        }
    }
}

@Composable
fun M3GalleryAppRoot() {
    M3GalleryTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            M3NavGraph()
        }
    }
}
