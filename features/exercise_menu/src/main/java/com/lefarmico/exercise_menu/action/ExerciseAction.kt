package com.lefarmico.exercise_menu.action

import com.lefarmico.core.base.BaseAction
import com.lefarmico.core.entity.LibraryViewData

sealed class ExerciseAction : BaseAction {

    data class GetExercises(val subcategoryId: Int) : ExerciseAction()

    data class ClickItem(
        val item: LibraryViewData.Exercise,
        val isFromWorkoutScreen: Boolean
    ) : ExerciseAction()

    data class CreateNewExercise(
        val subcategoryId: Int,
        val isFromWorkoutScreen: Boolean
    ) : ExerciseAction()

    data class ShowToast(val text: String) : ExerciseAction()
}
