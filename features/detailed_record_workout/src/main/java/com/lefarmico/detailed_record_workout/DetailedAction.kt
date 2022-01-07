package com.lefarmico.detailed_record_workout

import com.lefarmico.core.base.BaseAction

sealed class DetailedAction : BaseAction {
    data class GetWorkout(val workoutId: Int) : DetailedAction()
    data class ShowToast(val text: String) : DetailedAction()
}
