package com.lefarmico.workout

import com.lefarmico.core.base.BaseIntent
import com.lefarmico.navigation.params.SetParameterParams

sealed class WorkoutIntent : BaseIntent {

    data class AddExercise(val id: Int) : WorkoutIntent()

    data class DeleteExercise(val id: Int) : WorkoutIntent()

    data class AddSetToExercise(val params: SetParameterParams) : WorkoutIntent()

    data class DeleteLastSet(val exerciseId: Int) : WorkoutIntent()

    object GetExercises : WorkoutIntent()

    object GoToCategoryScreen : WorkoutIntent()

    object FinishWorkout : WorkoutIntent()

    data class GoToExerciseInfo(val libraryId: Int) : WorkoutIntent()

    data class ShowToast(val text: String) : WorkoutIntent()

    data class SwitchTimeScheduler(val isOn: Boolean) : WorkoutIntent()

    data class StartSetParameterDialog(val exerciseId: Int) : WorkoutIntent()

    object StartCalendarPickerDialog : WorkoutIntent()

    object StartWorkoutTitleDialog : WorkoutIntent()

    object StartTimePickerDialog : WorkoutIntent()

    object GetSelectedDate : WorkoutIntent()

    object GetTitle : WorkoutIntent()

    object GetTime : WorkoutIntent()

    data class CloseWorkout(val workoutId: Int) : WorkoutIntent()

    data class EditState(val action: Action) : WorkoutIntent() {
        sealed class Action {
            object Show : Action()
            object Hide : Action()
            object SelectAll : Action()
            object DeselectAll : Action()
            object DeleteSelected : Action()
        }
    }
}
