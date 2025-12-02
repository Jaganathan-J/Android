package com.fitlife.app.presentation.screens.execution

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitlife.app.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun ActiveProgressScreen(
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    // Timer Logic State
    var timeLeft by remember { mutableLongStateOf(84L) } // 01:24 in seconds
    var isPaused by remember { mutableStateOf(false) }
    val totalTime = 84L
    
    LaunchedEffect(key1 = timeLeft, key2 = isPaused) {
        if (timeLeft > 0 && !isPaused) {
            delay(1000L)
            timeLeft--
        }
    }

    val progress = timeLeft.toFloat() / totalTime.toFloat()
    val formattedTime = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(24.dp)
    ) {
        // Header
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = PurpleDark,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBack() }
            )
            Text(
                text = "810...", // Metric from UI analysis
                color = PurpleDark,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Main Circle Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                 Text(
                    text = "Progress",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurpleDark,
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 24.dp)
                )

                Box(contentAlignment = Alignment.Center) {
                    // Custom Circular Progress
                    Canvas(modifier = Modifier.size(280.dp)) {
                        val strokeWidth = 30.dp.toPx()
                        val radius = size.minDimension / 2 - strokeWidth / 2
                        
                        // Background Track
                        drawCircle(
                            color = LavenderAccent,
                            radius = radius,
                            style = Stroke(width = strokeWidth)
                        )

                        // Progress Arc
                        drawArc(
                            color = PurpleDark,
                            startAngle = -90f,
                            sweepAngle = 360 * progress,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                            topLeft = Offset(strokeWidth/2, strokeWidth/2),
                            size = Size(size.width - strokeWidth, size.height - strokeWidth)
                        )
                    }
                    // Timer Text
                    Text(
                        text = formattedTime,
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurpleDark
                    )
                }
            }
        }

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { isPaused = !isPaused },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CoralAction),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = if (isPaused) "Resume" else "Pause", fontSize = 18.sp)
            }
            
            Button(
                onClick = onSkip,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LavenderLight),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Skip", fontSize = 18.sp, color = PurpleDark)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}