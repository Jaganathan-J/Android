package com.fitlife.app.presentation.ui.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitlife.app.presentation.theme.DeepIndigo
import com.fitlife.app.presentation.theme.Lavender

@Composable
fun SummaryScreen(onBack: () -> Unit) {
    // Matches Screen 9: Summary Card
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Summary", style = MaterialTheme.typography.displayLarge, fontSize = 32.sp)
        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Lavender.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Good job!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DeepIndigo)
            Spacer(modifier = Modifier.height(24.dp))
            Text("01:24", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = DeepIndigo)
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = DeepIndigo),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Done")
            }
        }
    }
}