package com.example.fitlife.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.WorkoutRoutine
import com.example.fitlife.domain.repository.FitnessRepository
import com.example.fitlife.ui.theme.Indigo40
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutListViewModel @Inject constructor(private val repository: FitnessRepository) : ViewModel() {
    val routines = MutableStateFlow<List<WorkoutRoutine>>(emptyList())
    init {
        viewModelScope.launch {
            repository.getRoutines("c1").collect { routines.value = it } // Using dummy ID
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutListScreen(
    onBack: () -> Unit,
    onRoutineClick: (String) -> Unit,
    viewModel: WorkoutListViewModel = hiltViewModel()
) {
    val routines = viewModel.routines.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Full Body Workout", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Indigo40)
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(routines) { routine ->
                ListItem(
                    headlineContent = { Text(routine.name, fontWeight = FontWeight.SemiBold) },
                    supportingContent = if (routine.subtitle.isNotEmpty()) { { Text(routine.subtitle) } } else null,
                    leadingContent = {
                        Surface(modifier = Modifier.size(48.dp), color = Color.LightGray, shape = MaterialTheme.shapes.small) {}
                    },
                    trailingContent = {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null)
                    },
                    modifier = Modifier.clickable { onRoutineClick(routine.id) }
                )
                HorizontalDivider(thickness = 0.5.dp)
            }
        }
    }
}