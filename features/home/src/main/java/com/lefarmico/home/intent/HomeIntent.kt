package com.lefarmico.home.intent

import com.lefarmico.core.base.BaseIntent

sealed class HomeIntent : BaseIntent() {

    object GetWorkoutRecords : HomeIntent()

    object StartWorkoutScreen : HomeIntent()

    data class DetailsWorkout(val workoutId: Int) : HomeIntent()

    data class RemoveWorkout(val workoutId: Int) : HomeIntent()
}
