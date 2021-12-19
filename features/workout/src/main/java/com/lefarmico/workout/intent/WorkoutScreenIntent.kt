package com.lefarmico.workout.intent

import com.lefarmico.core.base.BaseIntent
import com.lefarmico.core.toolbar.EditActionBarEvents
import com.lefarmico.navigation.dialog.Dialog
import com.lefarmico.navigation.params.SetParameterParams

sealed class WorkoutScreenIntent : BaseIntent() {

    data class AddExercise(val id: Int) : WorkoutScreenIntent()

    data class DeleteExercise(val id: Int) : WorkoutScreenIntent()

    data class AddSetToExercise(
        val params: SetParameterParams
    ) : WorkoutScreenIntent()

    data class DeleteLastSet(val exerciseId: Int) : WorkoutScreenIntent()

    object GetExercises : WorkoutScreenIntent()

    object GoToCategoryScreen : WorkoutScreenIntent()

    object FinishWorkout : WorkoutScreenIntent()

    data class GoToExerciseInfo(val libraryId: Int) : WorkoutScreenIntent()

    data class ShowToast(val text: String) : WorkoutScreenIntent()

    data class ShowDialog(val dialog: Dialog) : WorkoutScreenIntent()

    data class ActionBarEvent(val event: EditActionBarEvents) : WorkoutScreenIntent()

    data class StartSetParameterDialog(val exerciseId: Int) : WorkoutScreenIntent()

    object StartCalendarPickerDialog : WorkoutScreenIntent()

    object StartWorkoutTitleDialog : WorkoutScreenIntent()

    object GetSelectedDate : WorkoutScreenIntent()
}
