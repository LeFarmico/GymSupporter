package com.lefarmico.workout.intent

import androidx.fragment.app.FragmentManager
import com.lefarmico.core.base.BaseIntent
import com.lefarmico.core.dialog.setParameter.SetSettingDialogCallback
import com.lefarmico.core.toolbar.RemoveActionBarEvents

sealed class WorkoutScreenIntent : BaseIntent() {

    data class AddExercise(val id: Int) : WorkoutScreenIntent()

    data class DeleteExercise(val id: Int) : WorkoutScreenIntent()

    data class AddSetToExercise(
        val exerciseId: Int,
        val reps: Int,
        val weight: Float
    ) : WorkoutScreenIntent()

    data class DeleteLastSet(val exerciseId: Int) : WorkoutScreenIntent()

    object GetAll : WorkoutScreenIntent()

    object GoToCategoryScreen : WorkoutScreenIntent()

    object FinishWorkout : WorkoutScreenIntent()

    data class GoToExerciseInfo(val libraryId: Int) : WorkoutScreenIntent()

    data class ShowToast(val text: String) : WorkoutScreenIntent()

    data class ShowSetParametersDialog(
        val fragmentManager: FragmentManager,
        val exerciseId: Int,
        val callback: SetSettingDialogCallback,
        val tag: String
    ) : WorkoutScreenIntent()

    data class ActionBarEvent(val event: RemoveActionBarEvents) : WorkoutScreenIntent()
}
