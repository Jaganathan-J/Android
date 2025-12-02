package com.example.fitlife.domain.model

data class UserProfile(
    val id: String,
    val name: String,
    val email: String
)

data class ExerciseCategory(
    val id: String,
    val name: String,
    // Normally would have a resource ID or Url
)

data class WorkoutRoutine(
    val id: String,
    val categoryId: String,
    val name: String,
    val subtitle: String = ""
)

data class Exercise(
    val id: String,
    val routineId: String,
    val name: String,
    val description: String,
    val durationSeconds: Int
)