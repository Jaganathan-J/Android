package com.example.autoflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autoflow.ui.theme.PrimaryPurple
import com.example.autoflow.ui.theme.SecondaryBlue

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val brush = Brush.linearGradient(listOf(PrimaryPurple, SecondaryBlue))
    
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.DarkGray
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(if (enabled) Modifier.background(brush) else Modifier),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = Color.White, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        }
    }
}