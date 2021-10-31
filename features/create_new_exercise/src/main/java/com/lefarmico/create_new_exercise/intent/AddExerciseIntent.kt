package com.lefarmico.create_new_exercise.intent

import com.lefarmico.core.base.BaseIntent

sealed class AddExerciseIntent : BaseIntent() {

    data class AddExerciseResult(
        val title: String,
        val description: String,
        val imageRes: String,
        val subcategoryId: Int
    ) : AddExerciseIntent()

    object Back : AddExerciseIntent()

    data class ShowToast(val text: String) : AddExerciseIntent()
}
