package com.fitlife.app.domain.model

data class UserProfile(
    val name: String,
    val email: String,
    val avatarUrl: String?
)

data class ExerciseCategory(
    val id: String,
    val name: String,
    val iconName: String
)

data class WorkoutSession(
    val id: String,
    val name: String,
    val startTimestamp: Long,
    val durationSeconds: Int
)