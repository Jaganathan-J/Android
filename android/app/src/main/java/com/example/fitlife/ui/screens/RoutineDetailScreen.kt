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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.repository.FitnessRepository
import com.example.fitlife.ui.theme.Indigo40
import com.example.fitlife.ui.theme.IndigoLight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineDetailViewModel @Inject constructor(private val repository: FitnessRepository) : ViewModel() {
    val exercises = MutableStateFlow<List<Exercise>>(emptyList())
    init {
        viewModelScope.launch {
            repository.getExercises("r1").collect { exercises.value = it }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineDetailScreen(
    onBack: () -> Unit,
    onExerciseClick: (String) -> Unit,
    viewModel: RoutineDetailViewModel = hiltViewModel()
) {
    val exercises = viewModel.exercises.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Indigo40)
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(exercises) { ex ->
                ListItem(
                    headlineContent = { Text(ex.name, fontWeight = FontWeight.SemiBold) },
                    supportingContent = { Text(ex.description) },
                    leadingContent = {
                        Surface(modifier = Modifier.size(48.dp), color = IndigoLight, shape = MaterialTheme.shapes.small) {}
                    },
                    trailingContent = {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null)
                    },
                    modifier = Modifier.clickable { onExerciseClick(ex.id) }
                )
                HorizontalDivider(thickness = 0.5.dp)
            }
        }
    }
}