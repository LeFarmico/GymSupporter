package com.lefarmico.home.viewData

sealed class WorkoutRecordViewData {

    data class Workout(
        val date: String,
        val exerciseList: List<Exercise> = mutableListOf()
    ) : WorkoutRecordViewData()

    data class Exercise(
        val exerciseName: String,
        val noteSetList: List<Set> // TODO : Попробовать через id
    ) : WorkoutRecordViewData()

    data class Set(
        val setNumber: Int,
        val weight: Float,
        val reps: Int,
        val measureType: MeasureType
    ) : WorkoutRecordViewData()

    enum class MeasureType(val typeNumber: Int) {
        KILO(1), LB(2)
    }
}
