package com.example.material3showcase.ui.components.chips

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.material3showcase.domain.model.ChipModel

@Composable
fun ChipsDemo() {
    val chips = remember {
        mutableStateOf(
            listOf(
                ChipModel("1", "Choice"),
                ChipModel("2", "Filter"),
                ChipModel("3", "Input"),
                ChipModel("4", "Action")
            )
        )
    }

    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        LazyRow {
            items(chips.value) { chip ->
                when (chip.label) {
                    "Filter" -> FilterChip(selected = chip.selected, onClick = {}, label = { Text(chip.label) }, modifier = Modifier.padding(end = 8.dp))
                    "Input" -> InputChip(selected = chip.selected, onClick = {}, label = { Text(chip.label) }, modifier = Modifier.padding(end = 8.dp))
                    "Action" -> SuggestionChip(onClick = {}, label = { Text(chip.label) }, modifier = Modifier.padding(end = 8.dp))
                    else -> FilterChip(selected = chip.selected, onClick = {}, label = { Text(chip.label) }, modifier = Modifier.padding(end = 8.dp))
                }
            }
        }
    }
    Text("Chips examples", style = MaterialTheme.typography.bodyLarge)
}
