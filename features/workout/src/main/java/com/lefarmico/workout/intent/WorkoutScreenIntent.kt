package com.lefarmico.workout.intent

import com.lefarmico.core.base.BaseIntent
import com.lefarmico.domain.entity.CurrentWorkoutDto

sealed class WorkoutScreenIntent : BaseIntent() {

    data class AddExercise(val id: Int) : WorkoutScreenIntent()

    data class DeleteExercise(val id: Int) : WorkoutScreenIntent()

    data class AddSetToExercise(
        val exerciseId: Int,
        val reps: Int,
        val weight: Float
    ) : WorkoutScreenIntent()

    data class DeleteSet(val set: CurrentWorkoutDto.Set) : WorkoutScreenIntent()

    data class GetExercise(val exerciseId: Int) : WorkoutScreenIntent()

    object GetAll : WorkoutScreenIntent()

    object GoToCategoryScreen : WorkoutScreenIntent()

    object FinishWorkout : WorkoutScreenIntent()
}
