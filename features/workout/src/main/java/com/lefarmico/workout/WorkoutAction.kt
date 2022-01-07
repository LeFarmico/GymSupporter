package com.lefarmico.workout

import com.lefarmico.core.base.BaseAction
import com.lefarmico.navigation.dialog.Dialog
import com.lefarmico.navigation.params.SetParameterParams

sealed class WorkoutAction : BaseAction {

    data class AddExercise(val id: Int) : WorkoutAction()

    data class DeleteExercise(val id: Int) : WorkoutAction()

    data class AddSetToExercise(val params: SetParameterParams) : WorkoutAction()

    data class DeleteLastSet(val exerciseId: Int) : WorkoutAction()

    object GetExercises : WorkoutAction()

    object GoToCategoryScreen : WorkoutAction()

    object FinishWorkout : WorkoutAction()

    data class GoToExerciseInfo(val libraryId: Int) : WorkoutAction()

    data class ShowToast(val text: String) : WorkoutAction()

    data class ShowDialog(val dialog: Dialog) : WorkoutAction()

    data class EditState(val action: Action) : WorkoutAction() {
        sealed class Action {
            object Show : Action()
            object Hide : Action()
            object SelectAll : Action()
            object DeselectAll : Action()
            object DeleteSelected : Action()
        }
    }

    data class StartSetParameterDialog(val exerciseId: Int) : WorkoutAction()

    object StartCalendarPickerDialog : WorkoutAction()

    object StartWorkoutTitleDialog : WorkoutAction()

    object GetSelectedDate : WorkoutAction()

    object GetTitle : WorkoutAction()
}
