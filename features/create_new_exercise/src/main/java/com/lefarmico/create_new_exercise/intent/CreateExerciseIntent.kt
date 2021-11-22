package com.lefarmico.create_new_exercise.intent

import com.lefarmico.core.base.BaseIntent

sealed class CreateExerciseIntent : BaseIntent() {

    data class ValidateExercise(
        val title: String,
        val subcategoryId: Int
    ) : CreateExerciseIntent()

    data class AddExercise(
        val title: String,
        val description: String,
        val imageRes: String,
        val subcategoryId: Int
    ) : CreateExerciseIntent()

    object Back : CreateExerciseIntent()

    data class ShowToast(val text: String) : CreateExerciseIntent()
}
