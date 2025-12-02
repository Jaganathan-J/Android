package com.example.fitlife.domain.model

data class WorkoutCategory(
    val id: String,
    val name: String,
    val iconRes: Int // Using resource IDs for simplicity in mock
)

data class Exercise(
    val id: String,
    val name: String,
    val durationSeconds: Int,
    val categoryId: String
)