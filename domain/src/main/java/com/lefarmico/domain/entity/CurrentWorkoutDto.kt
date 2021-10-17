package com.lefarmico.domain.entity

sealed class CurrentWorkoutDto {

    data class Exercise(
        val id: Int,
        val exerciseName: String
    ) : CurrentWorkoutDto()

    data class Set(
        val id: Int,
        val exerciseId: Int,
        val setNumber: Int,
        val weight: Float,
        val reps: Int,
        val measureType: MeasureType
    ) : CurrentWorkoutDto()

    enum class MeasureType(val typeNumber: Int) {
        KILO(1), LB(2)
    }

    data class ExerciseWithSets(
        val exercise: Exercise,
        val setList: List<Set>
    ) : CurrentWorkoutDto()
}
