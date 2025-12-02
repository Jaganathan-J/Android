package com.fitlife.app.domain.repository

import com.fitlife.app.domain.model.ExerciseCategory
import com.fitlife.app.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getCategories(): Flow<List<ExerciseCategory>>
    fun getUserProfile(): Flow<UserProfile>
}