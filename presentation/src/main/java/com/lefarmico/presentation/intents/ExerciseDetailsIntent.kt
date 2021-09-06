package com.lefarmico.presentation.intents

import com.lefarmico.presentation.views.base.BaseIntent

sealed class ExerciseDetailsIntent : BaseIntent() {
    
    data class GetExercise(val exerciseId: Int) : ExerciseDetailsIntent()
}
