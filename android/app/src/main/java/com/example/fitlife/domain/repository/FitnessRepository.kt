package com.example.fitlife.domain.repository

import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.model.ExerciseCategory
import com.example.fitlife.domain.model.UserProfile
import com.example.fitlife.domain.model.WorkoutRoutine
import kotlinx.coroutines.flow.Flow

interface FitnessRepository {
    fun getCurrentUser(): Flow<UserProfile>
    fun getCategories(): Flow<List<ExerciseCategory>>
    fun getRoutines(categoryId: String): Flow<List<WorkoutRoutine>>
    fun getExercises(routineId: String): Flow<List<Exercise>>
    fun getExercise(exerciseId: String): Flow<Exercise?>
}