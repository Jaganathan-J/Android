package com.example.fitlife.presentation.screens.execution

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.fitlife.presentation.components.ScreenHeader
import com.example.fitlife.presentation.theme.CoralAccent
import com.example.fitlife.presentation.theme.DeepIndigo
import com.example.fitlife.presentation.theme.LavenderLight

@Composable
fun ActiveProgressScreen(onFinish: () -> Unit, onBack: () -> Unit) {
    var timeLeft by remember { mutableStateOf(84) } // 1m 24s
    var isPaused by remember { mutableStateOf(false) }
    
    // Timer logic
    LaunchedEffect(timeLeft, isPaused) {
        if (timeLeft > 0 && !isPaused) {
            delay(1000L)
            timeLeft--
        } else if (timeLeft == 0) {
             onFinish()
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        ScreenHeader(title = "Progress", onBack = onBack)
        
        Column(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center) {
                // Circular Progress
                Canvas(modifier = Modifier.size(280.dp)) {
                    drawCircle(
                        color = LavenderLight,
                        style = Stroke(width = 20.dp.toPx())
                    )
                    drawArc(
                        color = DeepIndigo,
                        startAngle = -90f,
                        sweepAngle = (timeLeft / 84f) * 360f,
                        useCenter = false,
                        style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                
                Text(
                    text = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepIndigo
                )
            }
            
            Spacer(modifier = Modifier.height(60.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { isPaused = !isPaused },
                    colors = ButtonDefaults.buttonColors(containerColor = CoralAccent),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.height(50.dp).width(140.dp)
                ) {
                    Text(if (isPaused) "Resume" else "Pause", fontSize = 18.sp)
                }
                
                Button(
                    onClick = onFinish,
                    colors = ButtonDefaults.buttonColors(containerColor = LavenderLight),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.height(50.dp).width(140.dp)
                ) {
                    Text("Skip", color = DeepIndigo, fontSize = 18.sp)
                }
            }
        }
    }
}