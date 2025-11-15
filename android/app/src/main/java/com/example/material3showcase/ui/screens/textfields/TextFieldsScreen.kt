package com.example.material3showcase.ui.screens.textfields

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.material3showcase.domain.model.TextFieldConfig

@Composable
fun TextFieldsScreen(
    uiState: TextFieldsUiState,
    onEvent: (TextFieldsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Text Fields", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Outlined and filled text fields with icons and error states.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> CircularProgressIndicator()
            uiState.globalError != null -> {
                Text(text = uiState.globalError, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = { onEvent(TextFieldsEvent.Retry) }) {
                    Text("Retry")
                }
            }
            else -> {
                uiState.fields.forEachIndexed { index, config ->
                    val value = uiState.values[config.id] ?: ""
                    val error = uiState.errorForField[config.id]
                    TextFieldItem(
                        config = config,
                        value = value,
                        error = error,
                        filled = index % 2 == 0,
                        onValueChange = { onEvent(TextFieldsEvent.OnValueChange(config.id, it)) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun TextFieldItem(
    config: TextFieldConfig,
    value: String,
    error: String?,
    filled: Boolean,
    onValueChange: (String) -> Unit
) {
    val isPassword = config.isPassword
    val passwordVisible = remember { mutableStateOf(false) }

    val trailingIcon: (@Composable (() -> Unit))? = if (isPassword) {
        {
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(
                    imageVector = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (passwordVisible.value) "Hide password" else "Show password"
                )
            }
        }
    } else null

    val supporting = error ?: config.supportingText

    AnimatedVisibility(visible = true, enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessLow)), exit = fadeOut()) {
        if (filled) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(config.label) },
                modifier = Modifier.fillMaxWidth(),
                isError = error != null,
                supportingText = { supporting?.let { Text(it) } },
                placeholder = { Text(config.placeholder) },
                trailingIcon = trailingIcon,
                singleLine = true
            )
        } else {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(config.label) },
                modifier = Modifier.fillMaxWidth(),
                isError = error != null,
                supportingText = { supporting?.let { Text(it) } },
                placeholder = { Text(config.placeholder) },
                trailingIcon = trailingIcon,
                singleLine = true
            )
        }
    }
}
