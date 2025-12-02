package com.example.fitlife.presentation.screens.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.model.WorkoutCategory
import com.example.fitlife.domain.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<WorkoutCategory>>(emptyList())
    val categories: StateFlow<List<WorkoutCategory>> = _categories

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises

    init {
        fetchCategories()
        fetchExercises("1") // Default for demo
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            repository.getCategories().collect { _categories.value = it }
        }
    }

    private fun fetchExercises(categoryId: String) {
        viewModelScope.launch {
            repository.getExercisesForCategory(categoryId).collect { _exercises.value = it }
        }
    }
}