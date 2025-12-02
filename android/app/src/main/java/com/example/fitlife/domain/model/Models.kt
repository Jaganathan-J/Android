package com.example.fitlife.domain.model

data class WorkoutCategory(
    val id: String,
    val name: String,
    val iconName: String // Simplified for mock
)

data class Exercise(
    val id: String,
    val name: String,
    val details: String,
    val durationSeconds: Int
)