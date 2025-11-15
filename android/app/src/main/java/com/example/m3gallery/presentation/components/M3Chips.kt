package com.example.m3gallery.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SingleAssistChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AssistChip(
        onClick = onClick,
        label = { Text(label) },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors()
    )
}

@Composable
fun FilterChipsRow(
    options: List<String>,
    selected: String?,
    onSelectedChanged: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        options.forEach { option ->
            FilterChip(
                selected = selected == option,
                onClick = {
                    onSelectedChanged(if (selected == option) null else option)
                },
                label = { Text(option) },
                modifier = Modifier.padding(end = 8.dp),
                colors = FilterChipDefaults.filterChipColors()
            )
        }
    }
}
