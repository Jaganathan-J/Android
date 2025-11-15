package com.example.m3gallery.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.m3gallery.presentation.navigation.Screen

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Material 3 Components Gallery",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Explore Material 3 components including buttons, cards, dialogs and more.",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(onClick = { onNavigate(Screen.Buttons.route) }) {
            Text(text = "View Buttons")
        }

        Button(onClick = { onNavigate(Screen.Cards.route) }) {
            Text(text = "View Cards")
        }

        Button(onClick = { onNavigate(Screen.Dialogs.route) }) {
            Text(text = "View Dialogs")
        }
    }
}
