package com.lefarmico.create_new_exercise

import com.lefarmico.core.base.BaseIntent

sealed class CreateExerciseIntent : BaseIntent {

    data class GetExercises(val subcategoryId: Int) : CreateExerciseIntent()

    data class ValidateExercise(val title: String) :
        CreateExerciseIntent()

    data class AddExercise(
        val title: String,
        val description: String,
        val imageRes: String,
        val subcategoryId: Int
    ) : CreateExerciseIntent()

    data class CloseScreenWithToast(val text: String) : CreateExerciseIntent()

    data class ShowToast(val text: String) : CreateExerciseIntent()
}
