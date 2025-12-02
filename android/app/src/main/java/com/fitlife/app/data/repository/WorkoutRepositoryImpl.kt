package com.fitlife.app.data.repository

import com.fitlife.app.domain.model.ExerciseCategory
import com.fitlife.app.domain.model.UserProfile
import com.fitlife.app.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor() : WorkoutRepository {

    // Hardcoded to match Wireframe Screen 5 Analysis
    override fun getCategories(): Flow<List<ExerciseCategory>> = flow {
        emit(listOf(
            ExerciseCategory("1", "Jump Rope", "fitness_center"),
            ExerciseCategory("2", "Deadlift", "fitness_center"),
            ExerciseCategory("3", "Bench Press", "fitness_center"),
            ExerciseCategory("4", "Burpees", "fitness_center"),
            ExerciseCategory("5", "Bicep Curl", "fitness_center"), // Corrected typo from wireframe
            ExerciseCategory("6", "Mountain Climbers", "fitness_center") // Corrected typo
        ))
    }

    // Hardcoded to match Wireframe Screen 3 Analysis
    override fun getUserProfile(): Flow<UserProfile> = flow {
        emit(UserProfile(
            name = "Emma Johnson",
            email = "emmme@email.com",
            avatarUrl = null
        ))
    }
}