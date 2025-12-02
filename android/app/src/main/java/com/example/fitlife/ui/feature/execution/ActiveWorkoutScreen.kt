package com.example.fitlife.ui.feature.execution

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.* // for LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.fitlife.ui.theme.BrandCoral
import com.example.fitlife.ui.theme.BrandLavender
import com.example.fitlife.ui.theme.BrandPurple
import com.example.fitlife.ui.navigation.Screen

@Composable
fun ActiveWorkoutScreen(navController: NavController) {
    var ticks by remember { mutableIntStateOf(84) } // 1:24
    var isPaused by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = ticks, key2 = isPaused) {
        if (ticks > 0 && !isPaused) {
            delay(1000L)
            ticks--
        } else if (ticks == 0) {
            navController.navigate(Screen.Summary.route)
        }
    }

    val progress = ticks / 100f // Arbitrary max 100s for demo
    val minutes = ticks / 60
    val seconds = ticks % 60
    val timeString = String.format("%02d:%02d", minutes, seconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Progress", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterVertically))
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(250.dp)) {
                drawArc(
                    color = BrandLavender,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 20.dp.toPx())
                )
                drawArc(
                    color = BrandPurple,
                    startAngle = -90f,
                    sweepAngle = progress * 360f,
                    useCenter = false,
                    style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
                )
            }
            Text(text = timeString, fontSize = 48.sp, fontWeight = FontWeight.Bold, color = BrandPurple)
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = { isPaused = !isPaused },
                modifier = Modifier.weight(1f).height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandCoral),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(if (isPaused) "Resume" else "Pause", fontSize = 20.sp)
            }
            
            Button(
                onClick = { navController.navigate(Screen.Summary.route) },
                modifier = Modifier.weight(1f).height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandLavender),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Skip", color = BrandPurple, fontSize = 20.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SummaryScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Good Job!", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = BrandPurple)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Workout Complete", fontSize = 20.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = { navController.navigate(Screen.Profile.route) { popUpTo(Screen.Profile.route) { inclusive = true } } },
            colors = ButtonDefaults.buttonColors(containerColor = BrandCoral),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Done")
        }
    }
}