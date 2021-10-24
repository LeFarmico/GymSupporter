package com.lefarmico.home.intent

import com.lefarmico.core.base.BaseIntent

sealed class HomeIntent : BaseIntent() {

    object GetWorkoutRecords : HomeIntent()

    object StartWorkoutScreen : HomeIntent()

    data class EditWorkout(val workoutId: Int) : HomeIntent()
}