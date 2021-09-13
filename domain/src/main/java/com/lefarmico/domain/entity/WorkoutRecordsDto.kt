package com.lefarmico.domain.entity

sealed class WorkoutRecordsDto {

    data class Workout(
        val id: Int = 0,
        val date: String = "",
        val exerciseList: List<Exercise> = mutableListOf()
    ) : WorkoutRecordsDto()

    data class Exercise(
        val id: Int,
        val exerciseName: String,
        val noteSetList: List<Set> // TODO : Попробовать через id
    ) : WorkoutRecordsDto()

    data class Set(
        val id: Int = 0,
        val exerciseId: Int,
        val setNumber: Int,
        val weight: Float,
        val reps: Int,
        val measureType: MeasureType
    ) : WorkoutRecordsDto()

    enum class MeasureType(val typeNumber: Int) {
        KILO(1), LB(2)
    }
}
