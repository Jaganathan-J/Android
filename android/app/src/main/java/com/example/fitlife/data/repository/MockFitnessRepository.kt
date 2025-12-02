package com.example.fitlife.data.repository

import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.model.ExerciseCategory
import com.example.fitlife.domain.model.UserProfile
import com.example.fitlife.domain.model.WorkoutRoutine
import com.example.fitlife.domain.repository.FitnessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockFitnessRepository @Inject constructor() : FitnessRepository {

    override fun getCurrentUser(): Flow<UserProfile> = flow {
        emit(UserProfile("u1", "Emma Johnson", "emme@email.com"))
    }

    override fun getCategories(): Flow<List<ExerciseCategory>> = flow {
        emit(listOf(
            ExerciseCategory("c1", "Jump Rope"),
            ExerciseCategory("c2", "Deadlift"),
            ExerciseCategory("c3", "Bench Press"),
            ExerciseCategory("c4", "Burpees"),
            ExerciseCategory("c5", "Bicep Curl"),
            ExerciseCategory("c6", "Mountain Climbers")
        ))
    }

    override fun getRoutines(categoryId: String): Flow<List<WorkoutRoutine>> = flow {
        emit(listOf(
            WorkoutRoutine("r1", categoryId, "Full Body Workout"),
            WorkoutRoutine("r2", categoryId, "Upper Body Strength"),
            WorkoutRoutine("r3", categoryId, "Lower Body Blast"),
            WorkoutRoutine("r4", categoryId, "Plank", "1 min")
        ))
    }

    override fun getExercises(routineId: String): Flow<List<Exercise>> = flow {
        emit(listOf(
            Exercise("e1", routineId, "Squats", "Sets 3 reps 12", 60),
            Exercise("e2", routineId, "Push Ups", "Sets 4 reps 10", 45),
            Exercise("e3", routineId, "Lunges", "Sets 3 reps 12", 60),
            Exercise("e4", routineId, "Plank", "1 min", 60)
        ))
    }

    private val allExercises = listOf(
        Exercise("e1", "r1", "Squats", "Sets 3 reps 12", 60),
        Exercise("e2", "r1", "Push Ups", "Sets 4 reps 10", 45),
        Exercise("e3", "r1", "Lunges", "Sets 3 reps 12", 60),
        Exercise("e4", "r1", "Plank", "1 min", 60)
    )

    override fun getExercise(exerciseId: String): Flow<Exercise?> = flow {
        emit(allExercises.find { it.id == exerciseId })
    }
}