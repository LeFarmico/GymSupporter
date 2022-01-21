package com.lefarmico.navigation.params

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class WorkoutScreenParams : Parcelable {

    @Parcelize
    data class NewExercise(
        val id: Int,
    ) : WorkoutScreenParams()

    @Parcelize
    object Empty : WorkoutScreenParams()

    @Parcelize
    data class UpdateWorkout(val recordWorkoutId: Int) : WorkoutScreenParams()
}
