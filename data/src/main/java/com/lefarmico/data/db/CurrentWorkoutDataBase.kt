package com.lefarmico.data.db

import com.lefarmico.data.db.entity.CurrentWorkoutData

class CurrentWorkoutDataBase {

    val exerciseWithSetsList = mutableListOf<CurrentWorkoutData.ExerciseWithSets>()
    var exerciseId = 1

    fun insertExerciseWithSets(exercise: CurrentWorkoutData.ExerciseWithSets) {
        val currentExercise = CurrentWorkoutData.Exercise.Builder()
            .setId(exerciseId++)
            .setTitle(exercise.exercise.title)
            .setLibraryId(exercise.exercise.libraryId)
            .build()

        exerciseWithSetsList.add(
            CurrentWorkoutData.ExerciseWithSets(
                currentExercise,
                exercise.setList
            )
        )
    }
    companion object {
        const val LOADING = 2L
        const val SUCCESS = 1L
        const val EMPTY = 0L
        const val ERROR = -1L
    }
}
