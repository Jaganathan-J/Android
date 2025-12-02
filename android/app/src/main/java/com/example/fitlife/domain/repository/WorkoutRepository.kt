package com.example.fitlife.domain.repository

import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.model.WorkoutCategory
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getCategories(): Flow<List<WorkoutCategory>>
    fun getExercisesByCategory(categoryId: String): Flow<List<Exercise>>
    fun getExerciseById(exerciseId: String): Flow<Exercise?>
}