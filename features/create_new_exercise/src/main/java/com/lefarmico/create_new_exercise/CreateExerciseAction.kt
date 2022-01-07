package com.lefarmico.create_new_exercise

import com.lefarmico.core.base.BaseAction

sealed class CreateExerciseAction : BaseAction {
    data class ValidateExercise(val title: String) : CreateExerciseAction()

    data class GetExercises(val subcategoryId: Int) : CreateExerciseAction()

    data class AddExercise(
        val title: String,
        val description: String,
        val imageRes: String,
        val subcategoryId: Int
    ) : CreateExerciseAction()

    object Back : CreateExerciseAction()

    data class ShowToast(val text: String) : CreateExerciseAction()
}
