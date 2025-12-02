package com.example.fitlife.domain

import kotlinx.coroutines.flow.Flow

data class ExerciseCategory(val id: String, val name: String, val icon: String)
data class Exercise(val id: String, val name: String, val sets: Int, val reps: Int)

interface WorkoutRepository {
    fun getCategories(): List<ExerciseCategory>
    fun getExercisesForCategory(categoryId: String): List<Exercise>
}