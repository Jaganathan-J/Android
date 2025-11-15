package com.example.m3gallery.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m3gallery.presentation.components.ElevatedInfoCard

@Composable
fun HomeScreen(
    onNavigateToButtons: () -> Unit,
    onNavigateToCards: () -> Unit,
    onNavigateToDialogs: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Material 3 Component Gallery",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Explore how M3 components behave in different states.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(24.dp))

        ElevatedInfoCard(
            title = "Buttons",
            body = "Filled, elevated, outlined and text buttons with loading and error states.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            onClick = onNavigateToButtons
        )

        ElevatedInfoCard(
            title = "Cards",
            body = "Elevated and filled cards with icons and text.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            onClick = onNavigateToCards
        )

        ElevatedInfoCard(
            title = "Dialogs",
            body = "Confirmation, warning and input dialogs.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            onClick = onNavigateToDialogs
        )
    }
}
