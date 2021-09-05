package com.lefarmico.donetime.intents

import com.lefarmico.donetime.views.base.BaseIntent

sealed class ExerciseDetailsIntent : BaseIntent() {
    
    data class GetExercise(val exerciseId: Int) : ExerciseDetailsIntent()
}
