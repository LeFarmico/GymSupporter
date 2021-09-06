package com.lefarmico.presentation.intents

import com.lefarmico.presentation.views.base.BaseIntent

sealed class AddExerciseIntent : BaseIntent() {
    
    data class AddExerciseResult(
        val title: String,
        val description: String,
        val imageRes: String,
        val subcategoryId: Int
    ) : AddExerciseIntent()
    
    object DefaultState : AddExerciseIntent()
}
