package com.example.m3gallery.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    onNavigateToButtons: () -> Unit,
    onNavigateToCards: () -> Unit,
    onNavigateToDialogs: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Material 3 Component Gallery")

        HomeCard(title = "Buttons", description = "Explore different M3 button styles.", onClick = onNavigateToButtons)
        HomeCard(title = "Cards", description = "Showcase elevated and filled cards.", onClick = onNavigateToCards)
        HomeCard(title = "Dialogs", description = "Confirmation and input dialogs.", onClick = onNavigateToDialogs)
    }
}

@Composable
private fun HomeCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        onClick = onClick,
        colors = CardDefaults.cardColors()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = title)
            Text(text = description, textAlign = TextAlign.Start)
        }
    }
}
