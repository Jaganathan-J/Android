package com.example.fitlife.presentation.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.repository.WorkoutRepository
import com.example.fitlife.ui.theme.Lavender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineDetailsViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises = _exercises.asStateFlow()
    
    fun loadExercises(routineId: String) {
        viewModelScope.launch {
            _exercises.value = repository.getExercisesByRoutine(routineId)
        }
    }
}

@Composable
fun RoutineDetailsScreen(
    viewModel: RoutineDetailsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onExerciseStart: (String) -> Unit
) {
    val exercises by viewModel.exercises.collectAsState()
    
    LaunchedEffect(Unit) { 
        viewModel.loadExercises("r1") // Mock
    }
    
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        IconButton(onClick = onNavigateBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text("Routine Details", fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 16.dp))
        
        LazyColumn {
            items(exercises) { exercise ->
                ExerciseRow(exercise, onExerciseStart)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ExerciseRow(exercise: Exercise, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(exercise.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Lavender, RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(exercise.name, fontWeight = FontWeight.Bold)
            Text(exercise.targetReps ?: "${exercise.targetSecs}s", color = Color.Gray)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
    }
}