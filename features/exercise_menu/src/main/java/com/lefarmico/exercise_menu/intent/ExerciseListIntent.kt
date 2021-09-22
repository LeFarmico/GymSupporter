package com.lefarmico.exercise_menu.intent

import com.lefarmico.core.base.BaseIntent

sealed class ExerciseListIntent : BaseIntent() {

    data class GetExercises(val subcategoryId: Int) : ExerciseListIntent()

    object CleanAll : ExerciseListIntent()

    data class GoToExerciseDetailsScreen(val exerciseId: Int) : ExerciseListIntent()

    data class AddExerciseToWorkoutScreen(val exerciseId: Int) : ExerciseListIntent()
}
