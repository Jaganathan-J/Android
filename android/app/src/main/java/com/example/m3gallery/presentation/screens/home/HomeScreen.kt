package com.example.m3gallery.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m3gallery.presentation.navigation.Screen

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    onNavigate: (String) -> Unit
) {
    val features = listOf(
        Screen.Buttons,
        Screen.Cards,
        Screen.Dialogs
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = "Material 3 Component Gallery",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Explore common components built with Jetpack Compose.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        items(features) { screen ->
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillParentMaxWidth(),
                onClick = { onNavigate(screen.route) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Icon(
                        imageVector = Icons.Default.Smartphone,
                        contentDescription = null
                    )
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Navigate to ${screen.title} demo.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
