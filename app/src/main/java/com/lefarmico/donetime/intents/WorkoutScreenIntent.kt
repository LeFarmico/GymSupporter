package com.lefarmico.donetime.intents

import com.lefarmico.donetime.views.base.BaseIntent

sealed class WorkoutScreenIntent : BaseIntent() {

    data class AddExercise(val id: Int) : WorkoutScreenIntent()

    data class DeleteExercise(val id: Int) : WorkoutScreenIntent()
    
    data class AddSetToExercise(
        val exerciseId: Int,
        val reps: Int,
        val weight: Float
    ) : WorkoutScreenIntent()

    data class DeleteSet(val exerciseId: Int) : WorkoutScreenIntent()

    object GetAll : WorkoutScreenIntent()

    object SaveAll : WorkoutScreenIntent()
}
