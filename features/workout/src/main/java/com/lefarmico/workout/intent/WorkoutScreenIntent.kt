package com.lefarmico.workout.intent

import com.lefarmico.core.base.BaseIntent

sealed class WorkoutScreenIntent : BaseIntent() {

    data class AddExercise(val id: Int) : WorkoutScreenIntent()

    data class DeleteExercise(val id: Int) : WorkoutScreenIntent()

    data class AddSetToExercise(
        val exerciseId: Int,
        val reps: Int,
        val weight: Float
    ) : WorkoutScreenIntent()

    data class DeleteLastSet(val exerciseId: Int) : WorkoutScreenIntent()

    object GetAll : WorkoutScreenIntent()

    object GoToCategoryScreen : WorkoutScreenIntent()

    object FinishWorkout : WorkoutScreenIntent()

    data class GoToExerciseInfo(val libraryId: Int) : WorkoutScreenIntent()
}
