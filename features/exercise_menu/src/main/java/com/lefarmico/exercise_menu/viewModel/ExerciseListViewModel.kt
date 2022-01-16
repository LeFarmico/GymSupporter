package com.lefarmico.exercise_menu.viewModel

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.exercise_menu.intent.ExerciseIntent
import com.lefarmico.exercise_menu.intent.ExerciseIntent.*
import com.lefarmico.exercise_menu.reduce
import com.lefarmico.exercise_menu.state.LibraryListEvent
import com.lefarmico.exercise_menu.state.LibraryListState
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.screen.Screen
import javax.inject.Inject

class ExerciseListViewModel @Inject constructor() : BaseViewModel<
    ExerciseIntent, LibraryListState, LibraryListEvent
    >() {

    @Inject
    lateinit var repo: LibraryRepository
    @Inject
    lateinit var router: Router

    private fun getExercises(subCategoryId: Int) {
        repo.getExercises(subCategoryId)
            .observeUi()
            .doOnSuccess { dataState -> mState.value = dataState.reduce() }
            .subscribe()
    }

    private fun onExerciseClick(exerciseLibraryId: Int, isFromWorkoutScreen: Boolean) {
        when (isFromWorkoutScreen) {
            true -> router.navigate(
                screen = Screen.ACTION_ADD_EXERCISE_TO_WORKOUT_SCREEN,
                data = WorkoutScreenParams.NewExercise(exerciseLibraryId)
            )
            false -> router.navigate(
                screen = Screen.EXERCISE_DETAILS_SCREEN,
                data = LibraryParams.Exercise(exerciseLibraryId)
            )
        }
    }

    private fun createNewExercise(subcategoryId: Int, isFromWorkoutScreen: Boolean) {
        router.navigate(
            screen = Screen.CREATE_NEW_EXERCISE_SCREEN,
            data = LibraryParams.NewExercise(subcategoryId, isFromWorkoutScreen)
        )
    }

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }

    override fun triggerIntent(intent: ExerciseIntent) {
        return when (intent) {
            is ClickItem -> onExerciseClick(intent.item.subCategoryId, intent.isFromWorkoutScreen)
            is CreateNewExercise -> createNewExercise(intent.subcategoryId, intent.isFromWorkoutScreen)
            is GetExercises -> getExercises(intent.subcategoryId)
            is ShowToast -> showToast(intent.text)
        }
    }
}
