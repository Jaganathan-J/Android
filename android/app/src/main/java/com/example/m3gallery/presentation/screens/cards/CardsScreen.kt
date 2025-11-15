package com.example.m3gallery.presentation.screens.cards

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
import com.example.m3gallery.presentation.components.FilledInfoCard

@Composable
fun CardsScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Cards", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Elevated and filled cards with support for click feedback.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(24.dp))

        ElevatedInfoCard(
            title = "Elevated Card",
            body = "This card raises above the surface when pressed.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        FilledInfoCard(
            title = "Filled Card",
            body = "Tonal filled card using secondary container colors.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        FilledInfoCard(
            title = "Back",
            body = "Return to previous screen.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            onClick = onBack
        )
    }
}
