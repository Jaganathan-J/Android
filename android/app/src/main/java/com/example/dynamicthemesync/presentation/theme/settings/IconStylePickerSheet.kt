package com.example.dynamicthemesync.presentation.theme.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dynamicthemesync.domain.model.IconStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconStylePickerSheet(
    uiState: ThemeUiState,
    onStyleSelected: (IconStyle) -> Unit,
    onAxesChanged: (Int?, Int?, Int?) -> Unit,
    onApply: () -> Unit,
    onCancel: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val selectedStyle = remember(uiState.iconStyle) { mutableStateOf(uiState.iconStyle) }
    val weight = remember(uiState.iconWeight) { mutableStateOf(uiState.iconWeight ?: 400) }
    val grade = remember(uiState.iconGrade) { mutableStateOf(uiState.iconGrade ?: 0) }
    val opsz = remember(uiState.iconOpticalSize) { mutableStateOf(uiState.iconOpticalSize ?: 24) }

    Surface {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = onCancel) { Text(text = "Cancel") }
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Icons")
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {
                    onStyleSelected(selectedStyle.value)
                    onAxesChanged(weight.value, grade.value, opsz.value)
                    onApply()
                }) { Text(text = "Apply") }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Style")
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                uiState.availableIconStyles.forEach { style ->
                    Text(
                        text = style.name.lowercase().replaceFirstChar { it.titlecase() },
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedStyle.value = style }
                            .padding(8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Weight (${weight.value})")
            Slider(value = weight.value.toFloat(), onValueChange = { weight.value = it.toInt() }, valueRange = 100f..700f)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Grade (${grade.value})")
            Slider(value = grade.value.toFloat(), onValueChange = { grade.value = it.toInt() }, valueRange = -50f..200f)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Optical Size (${opsz.value})")
            Slider(value = opsz.value.toFloat(), onValueChange = { opsz.value = it.toInt() }, valueRange = 20f..48f)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                onStyleSelected(selectedStyle.value)
                onAxesChanged(weight.value, grade.value, opsz.value)
                onApply()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Apply")
            }
        }
    }
}