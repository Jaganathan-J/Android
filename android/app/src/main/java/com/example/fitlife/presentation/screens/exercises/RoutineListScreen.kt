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
import com.example.fitlife.domain.model.WorkoutRoutine
import com.example.fitlife.domain.repository.WorkoutRepository
import com.example.fitlife.ui.theme.Lavender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineListViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {
    private val _routines = MutableStateFlow<List<WorkoutRoutine>>(emptyList())
    val routines = _routines.asStateFlow()
    
    fun loadRoutines(catId: String) {
        viewModelScope.launch {
            _routines.value = repository.getRoutinesByCategory(catId)
        }
    }
}

@Composable
fun RoutineListScreen(
    viewModel: RoutineListViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onRoutineClick: (String) -> Unit
) {
    val routines by viewModel.routines.collectAsState()
    
    // Trigger load. In a real app we'd get argument from SavedStateHandle
    LaunchedEffect(Unit) {
        viewModel.loadRoutines("1") // Mock ID for demonstration
    }
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                "Full Body Workout", 
                fontSize = 20.sp, 
                fontWeight = FontWeight.Bold, 
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(routines) { routine ->
                RoutineItem(routine, onRoutineClick)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun RoutineItem(routine: WorkoutRoutine, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(routine.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Lavender, RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(routine.name, fontWeight = FontWeight.Bold)
            Text("${routine.durationMinutes} min", color = Color.Gray, fontSize = 12.sp)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
    }
}