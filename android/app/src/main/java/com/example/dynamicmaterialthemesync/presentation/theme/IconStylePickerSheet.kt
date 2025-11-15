package com.example.dynamicmaterialthemesync.presentation.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dynamicmaterialthemesync.domain.model.IconStyle

@Composable
fun IconStylePickerSheet(
    uiState: ThemeUiState,
    onSelectStyle: (IconStyle) -> Unit,
    onApply: () -> Unit,
    onCancel: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Icon Style", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f, fill = false)) {
            items(uiState.availableIconStyles) { style ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onSelectStyle(style) }
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Text(text = style.name)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onApply, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Apply")
        }
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = onCancel, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Cancel")
        }
    }
}