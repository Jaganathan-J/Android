package com.example.fitlife.domain.model

data class ExerciseCategory(
    val id: String,
    val name: String,
    val iconName: String // Simplified for UI resource lookup
)

data class WorkoutRoutine(
    val id: String,
    val name: String,
    val categoryId: String,
    val durationMinutes: Int,
    val description: String = ""
)

data class Exercise(
    val id: String,
    val name: String,
    val routineId: String,
    val targetSecs: Int,
    val targetReps: String? = null,
    val description: String = ""
)