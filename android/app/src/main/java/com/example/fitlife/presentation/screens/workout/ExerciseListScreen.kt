package com.example.fitlife.presentation.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.* 
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitlife.presentation.components.ScreenHeader
import com.example.fitlife.presentation.theme.DeepIndigo
import com.example.fitlife.presentation.theme.LavenderLight
import com.example.fitlife.presentation.theme.LightGray

@Composable
fun ExerciseListScreen(
    viewModel: WorkoutViewModel = hiltViewModel(),
    onExerciseSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    val exercises by viewModel.exercises.collectAsState()
    
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        ScreenHeader(title = "Full Body Workout", onBack = onBack)
        
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(exercises) { exercise ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onExerciseSelected(exercise.id) }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(LavenderLight)
                            .padding(12.dp)
                    ) {
                         Icon(Icons.Default.FitnessCenter, null, tint = DeepIndigo)
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Text(exercise.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DeepIndigo)
                        Text(exercise.details, fontSize = 14.sp, color = Color.Gray)
                    }
                    
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = DeepIndigo
                    )
                }
            }
        }
    }
}