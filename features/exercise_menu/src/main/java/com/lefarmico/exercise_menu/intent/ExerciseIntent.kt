package com.lefarmico.exercise_menu.intent

import com.lefarmico.core.base.BaseIntent
import com.lefarmico.core.entity.LibraryViewData

sealed class ExerciseIntent : BaseIntent {

    data class GetExercises(val subcategoryId: Int) : ExerciseIntent()

    data class ClickItem(
        val item: LibraryViewData.Exercise,
        val isFromWorkoutScreen: Boolean
    ) : ExerciseIntent()

    data class CreateNewExercise(
        val subcategoryId: Int,
        val isFromWorkoutScreen: Boolean
    ) : ExerciseIntent()

    data class ShowToast(val text: String) : ExerciseIntent()

    data class EditState(val action: Action) : ExerciseIntent() {
        sealed class Action {
            object Show : Action()
            object Hide : Action()
            object SelectAll : Action()
            object DeselectAll : Action()
            object DeleteSelected : Action()
        }
    }

    data class DeleteExercise(val exerciseId: Int, val subcategoryId: Int) : ExerciseIntent()
}
