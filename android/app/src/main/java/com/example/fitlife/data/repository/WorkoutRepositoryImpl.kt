package com.example.fitlife.data.repository

import com.example.fitlife.R
import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.model.WorkoutCategory
import com.example.fitlife.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor() : WorkoutRepository {

    private val categories = listOf(
        WorkoutCategory("1", "Jump Rope", android.R.drawable.ic_menu_compass),
        WorkoutCategory("2", "Deadlift", android.R.drawable.ic_menu_mylocation),
        WorkoutCategory("3", "Bench Press", android.R.drawable.ic_menu_agenda),
        WorkoutCategory("4", "Burpees", android.R.drawable.ic_menu_call),
        WorkoutCategory("5", "Bicep Curl", android.R.drawable.ic_menu_camera),
        WorkoutCategory("6", "Mountain Cl..", android.R.drawable.ic_menu_day)
    )

    private val exercises = listOf(
        Exercise("ex1", "Standard Squats", 60, "1"),
        Exercise("ex2", "Push Ups", 45, "1"),
        Exercise("ex3", "Lunges", 60, "1"),
        Exercise("ex4", "Plank", 90, "1")
    )

    override fun getCategories(): Flow<List<WorkoutCategory>> = flowOf(categories)

    override fun getExercisesByCategory(categoryId: String): Flow<List<Exercise>> = flowOf(exercises)

    override fun getExerciseById(exerciseId: String): Flow<Exercise?> = flow {
        emit(exercises.find { it.id == exerciseId } ?: exercises.first())
    }
}