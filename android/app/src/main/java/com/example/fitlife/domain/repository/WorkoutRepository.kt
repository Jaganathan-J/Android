package com.example.fitlife.domain.repository

import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.model.ExerciseCategory
import com.example.fitlife.domain.model.WorkoutRoutine

interface WorkoutRepository {
    suspend fun getCategories(): List<ExerciseCategory>
    suspend fun getRoutinesByCategory(catId: String): List<WorkoutRoutine>
    suspend fun getExercisesByRoutine(routineId: String): List<Exercise>
    suspend fun getExercise(id: String): Exercise?
}