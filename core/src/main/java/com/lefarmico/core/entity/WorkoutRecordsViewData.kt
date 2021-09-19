package com.lefarmico.core.entity

sealed class WorkoutRecordsViewData {

    data class Workout(
        val id: Int = 0,
        val date: String = "",
        val exerciseList: List<Exercise> = mutableListOf()
    ) : WorkoutRecordsViewData()

    data class Exercise(
        val id: Int,
        val exerciseName: String,
        val noteSetList: List<Set>
    ) : WorkoutRecordsViewData()

    data class Set(
        val id: Int = 0,
        val exerciseId: Int,
        val setNumber: Int,
        val weight: Float,
        val reps: Int,
        val measureType: MeasureType
    ) : WorkoutRecordsViewData()

    enum class MeasureType(val typeNumber: Int) {
        KILO(1), LB(2)
    }
}
