package com.example.fitlife.data.repository

import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.model.WorkoutCategory
import com.example.fitlife.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockWorkoutRepository : WorkoutRepository {
    
    private val categories = listOf(
        WorkoutCategory("1", "Full Body Workout", "accessibility"),
        WorkoutCategory("2", "Upper Body Strength", "fitness_center"),
        WorkoutCategory("3", "Lower Body Blast", "directions_run"),
        WorkoutCategory("4", "Core Power", "layers")
    )

    private val mockExercises = listOf(
        Exercise("101", "Squats", "Sets 4 reps", 60),
        Exercise("102", "Push Ups", "Sets 4 reps", 45),
        Exercise("103", "Lunges", "Sets 4 reps", 60),
        Exercise("104", "Plank", "1 min", 60)
    )

    override fun getCategories(): Flow<List<WorkoutCategory>> = flow { emit(categories) }

    override fun getExercisesForCategory(categoryId: String): Flow<List<Exercise>> = flow {
        emit(mockExercises) // Just returning same list for all categories for demo
    }

    override fun getExercise(exerciseId: String): Flow<Exercise?> = flow {
        emit(mockExercises.find { it.id == exerciseId })
    }
}