package com.example.fitlife.domain.repository

import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.model.WorkoutCategory
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getCategories(): Flow<List<WorkoutCategory>>
    fun getExercisesForCategory(categoryId: String): Flow<List<Exercise>>
    fun getExercise(exerciseId: String): Flow<Exercise?>
}