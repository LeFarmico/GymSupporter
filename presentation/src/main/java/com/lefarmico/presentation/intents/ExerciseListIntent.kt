package com.lefarmico.presentation.intents

import com.lefarmico.presentation.views.base.BaseIntent

sealed class ExerciseListIntent : BaseIntent() {

    data class GetExercises(val subcategoryId: Int) : ExerciseListIntent()

    object CleanAll : ExerciseListIntent()
}
