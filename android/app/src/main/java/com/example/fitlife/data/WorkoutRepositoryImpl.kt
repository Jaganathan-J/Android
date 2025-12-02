package com.example.fitlife.data

import com.example.fitlife.domain.Exercise
import com.example.fitlife.domain.ExerciseCategory
import com.example.fitlife.domain.WorkoutRepository

class WorkoutRepositoryImpl : WorkoutRepository {
    override fun getCategories(): List<ExerciseCategory> {
        return listOf(
            ExerciseCategory("1", "Jump Rope", "ic_jump_rope"),
            ExerciseCategory("2", "Deadlift", "ic_deadlift"),
            ExerciseCategory("3", "Bench Press", "ic_bench"),
            ExerciseCategory("4", "Burpees", "ic_burpee"),
            ExerciseCategory("5", "Bicep Curl", "ic_curl"),
            ExerciseCategory("6", "Mountain Climbers", "ic_mc")
        )
    }

    override fun getExercisesForCategory(categoryId: String): List<Exercise> {
        // Simulating data from Screen 6/7
        return listOf(
            Exercise("101", "Squats", 3, 12),
            Exercise("102", "Push Ups", 4, 15),
            Exercise("103", "Lunges", 3, 10),
            Exercise("104", "Plank", 3, 60)
        )
    }
}