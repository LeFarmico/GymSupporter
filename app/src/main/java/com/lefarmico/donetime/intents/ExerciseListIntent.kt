package com.lefarmico.donetime.intents

import com.lefarmico.donetime.views.base.BaseIntent

sealed class ExerciseListIntent : BaseIntent() {

    data class GetExercises(val subcategoryId: Int) : ExerciseListIntent()

    object CleanAll : ExerciseListIntent()
}
