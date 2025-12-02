package com.example.fitlife.presentation.screens.execution

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlife.presentation.components.PrimaryButton
import com.example.fitlife.presentation.components.ScreenHeader
import com.example.fitlife.presentation.theme.DeepIndigo
import com.example.fitlife.presentation.theme.LavenderLight

@Composable
fun SummaryScreen(onHome: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        ScreenHeader(title = "Summary", onBack = { })
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(LavenderLight.copy(alpha = 0.4f))
                    .padding(vertical = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Good job!",
                        fontSize = 24.sp,
                        color = DeepIndigo,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "01:24",
                        fontSize = 56.sp,
                        color = DeepIndigo,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    PrimaryButton(
                        text = "Complete",
                        onClick = onHome,
                        modifier = Modifier.width(160.dp)
                    )
                }
            }
        }
    }
}