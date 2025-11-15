package com.example.m3gallery.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun M3CardsDemo(
    onCardClicked: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCardClicked("ElevatedCard") }
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.CreditCard, contentDescription = null)
                Column {
                    Text("Elevated Card", style = MaterialTheme.typography.titleMedium)
                    Text("Shows elevation and shadow when pressed.", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCardClicked("OutlinedCard") }
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                Column {
                    Text("Outlined Card", style = MaterialTheme.typography.titleMedium)
                    Text("Lower emphasis container for grouping content.", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
