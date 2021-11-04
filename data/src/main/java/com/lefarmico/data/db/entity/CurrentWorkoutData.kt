package com.lefarmico.data.db.entity

sealed class CurrentWorkoutData {

    data class Exercise(
        val id: Int = 0,
        val libraryId: Int,
        val title: String
    ) : CurrentWorkoutData()

    data class Set(
        val id: Int,
        val exerciseId: Int,
        val setNumber: Int,
        val weight: Float,
        val reps: Int
    ) : CurrentWorkoutData()

    data class ExerciseWithSets(
        val exercise: Exercise,
        val setList: MutableList<Set> = mutableListOf()
    ) : CurrentWorkoutData()
}
