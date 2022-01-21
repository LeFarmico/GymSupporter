package com.lefarmico.detailed_record_workout

import com.lefarmico.core.base.BaseIntent

sealed class DetailedIntent : BaseIntent {

    data class GetWorkout(val workoutId: Int) : DetailedIntent()
    data class ShowToast(val text: String) : DetailedIntent()
    data class EditWorkout(val workoutId: Int) : DetailedIntent()
}
