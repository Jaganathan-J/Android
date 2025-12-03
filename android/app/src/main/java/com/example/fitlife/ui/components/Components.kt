package com.example.fitlife.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fitlife.ui.theme.*

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = SurfaceDark
        ),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (enabled) Brush.horizontalGradient(listOf(PrimaryPurple, PrimaryBlue))
                    else Brush.linearGradient(listOf(Color.Gray, Color.DarkGray))
                )
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextWhite
            )
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = TextGrey,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceDark,
                unfocusedContainerColor = SurfaceDark,
                focusedBorderColor = PrimaryPurple,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            )
        )
    }
}

@Composable
fun AppToolbar(
    title: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "←", // Using text arrow for simplicity over vector resource here
            fontSize = 24.sp,
            color = TextWhite,
            modifier = Modifier
                .clickable { onBack() }
                .padding(end = 16.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = TextWhite
        )
    }
}