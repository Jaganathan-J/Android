package com.example.fitlife.data.repository

import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.model.ExerciseCategory
import com.example.fitlife.domain.model.WorkoutRoutine
import com.example.fitlife.domain.repository.WorkoutRepository

class MockWorkoutRepository : WorkoutRepository {

    private val categories = listOf(
        ExerciseCategory("1", "Jump Rope", "jump_rope"),
        ExerciseCategory("2", "Deadlift", "monitor_weight"),
        ExerciseCategory("3", "Bench Press", "fitness_center"),
        ExerciseCategory("4", "Burpees", "directions_run"),
        ExerciseCategory("5", "Bicep Curl", "sports_gymnastics"),
        ExerciseCategory("6", "Mtn Climbers", "hiking")
    )

    private val routines = listOf(
        WorkoutRoutine("r1", "Full Body Workout", "1", 30),
        WorkoutRoutine("r2", "Upper Body Strength", "3", 45),
        WorkoutRoutine("r3", "Lower Body Blast", "2", 40),
        WorkoutRoutine("r4", "Plank Routine", "1", 5)
    )

    private val exercises = listOf(
        Exercise("e1", "Squats", "r1", 90, "Sets 4 reps"),
        Exercise("e2", "Push Ups", "r1", 60, "Sets 4 reps"),
        Exercise("e3", "Lunges", "r1", 90, "Sets 3 reps"),
        Exercise("e4", "Plank", "r4", 60, "10 sets")
    )

    override suspend fun getCategories() = categories
    override suspend fun getRoutinesByCategory(catId: String) = routines // return all for demo
    override suspend fun getExercisesByRoutine(routineId: String) = exercises
    override suspend fun getExercise(id: String) = exercises.find { it.id == id }
}