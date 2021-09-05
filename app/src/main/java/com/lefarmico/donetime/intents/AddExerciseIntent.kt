package com.lefarmico.donetime.intents

import com.lefarmico.donetime.views.base.BaseIntent

sealed class AddExerciseIntent : BaseIntent() {
    
    data class AddExerciseResult(
        val title: String,
        val description: String,
        val imageRes: String,
        val subcategoryId: Int
    ) : AddExerciseIntent()
    
    object DefaultState : AddExerciseIntent()
}
