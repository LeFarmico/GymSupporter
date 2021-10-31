package com.lefarmico.home.intent

import com.lefarmico.core.base.BaseIntent
import com.lefarmico.core.toolbar.RemoveActionBarEvents

sealed class HomeIntent : BaseIntent() {

    object GetWorkoutRecords : HomeIntent()

    object NavigateToWorkout : HomeIntent()

    data class NavigateToDetailsWorkout(val workoutId: Int) : HomeIntent()

    data class RemoveWorkout(val workoutId: Int) : HomeIntent()

    data class ActionBarEvent(val event: RemoveActionBarEvents) : HomeIntent()
}
