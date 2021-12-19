package com.lefarmico.domain.entity

import java.time.LocalDate

sealed class WorkoutRecordsDto {

    data class Workout(
        val id: Int = 0,
        val date: LocalDate,
        val title: String = ""
    ) : WorkoutRecordsDto()

    data class Exercise(
        val id: Int = 0,
        val workoutId: Int = 0,
        val exerciseName: String
    ) : WorkoutRecordsDto()

    data class Set(
        val id: Int = 0,
        val exerciseId: Int = 0,
        val setNumber: Int,
        val weight: Float,
        val reps: Int,
        val measureType: MeasureType
    ) : WorkoutRecordsDto()

    enum class MeasureType(val typeNumber: Int) {
        KILO(1), LB(2)
    }

    data class ExerciseWithSets(
        val exercise: Exercise,
        val setList: List<Set>
    ) : WorkoutRecordsDto()

    data class WorkoutWithExercisesAndSets(
        val workout: Workout,
        val exerciseWithSetsList: List<ExerciseWithSets>
    ) : WorkoutRecordsDto()
}
