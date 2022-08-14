package com.lefarmico.exercise_menu.viewModel

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.exercise_menu.intent.ExerciseIntent
import com.lefarmico.exercise_menu.intent.ExerciseIntent.*
import com.lefarmico.exercise_menu.reduce
import com.lefarmico.exercise_menu.reduceDto
import com.lefarmico.exercise_menu.state.LibraryListEvent
import com.lefarmico.exercise_menu.state.LibraryListState
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.screen.Screen
import java.lang.Exception
import javax.inject.Inject

class ExerciseListViewModel @Inject constructor(
    private val repo: LibraryRepository,
    private val router: Router
) : BaseViewModel<ExerciseIntent, LibraryListState, LibraryListEvent>() {

    override fun triggerIntent(intent: ExerciseIntent) {
        return when (intent) {
            is ClickItem -> onExerciseClick(intent.item.id, intent.isFromWorkoutScreen)
            is CreateNewExercise -> createExercise(intent.subcategoryId, intent.isFromWorkoutScreen)
            is GetExercises -> getExercises(intent.subcategoryId)
            is ShowToast -> showToast(intent.text)
            is DeleteExercise -> deleteExercise(intent.exerciseId, intent.subcategoryId)
            is EditState -> editStateAction(intent.action)
            is GetSubcategoryTitle -> getSubcategoryTitle(intent.subcategoryId)
        }
    }

    private fun postStateEvent(baseState: BaseState) {
        when (baseState) {
            is BaseState.Event -> mEvent.postValue(baseState as LibraryListEvent)
            is BaseState.State -> mState.value = baseState as LibraryListState
        }
    }

    private fun getSubcategoryTitle(subcategoryId: Int) {
        repo.getSubCategory(subcategoryId)
            .observeUi()
            .doOnError { postStateEvent(LibraryListEvent.ExceptionResult(it as Exception)) }
            .doAfterSuccess { dataState -> postStateEvent(dataState.reduce()) }
            .subscribe()
    }

    private fun getExercises(subCategoryId: Int) {
        repo.getExercises(subCategoryId)
            .observeUi()
            .doOnError { postStateEvent(LibraryListEvent.ExceptionResult(it as Exception)) }
            .doOnSubscribe { postStateEvent(LibraryListState.Loading) }
            .doOnSuccess { dataState -> postStateEvent(dataState.reduceDto()) }
            .subscribe()
    }

    private fun onExerciseClick(exerciseLibraryId: Int, isFromWorkoutScreen: Boolean) {
        when (isFromWorkoutScreen) {
            true -> router.navigate(
                screen = Screen.ACTION_ADD_EXERCISE_TO_WORKOUT_SCREEN,
                data = WorkoutScreenParams.NewExercise(exerciseLibraryId)
            )
            false -> router.navigate(
                screen = Screen.FROM_LIBRARY_TO_EXERCISE_DETAILS,
                data = LibraryParams.Exercise(exerciseLibraryId)
            )
        }
    }

    private fun createExercise(subcategoryId: Int, isFromWorkoutScreen: Boolean) {
        if (isFromWorkoutScreen) {
            router.navigate(
                screen = Screen.CREATE_NEW_EXERCISE_SCREEN,
                data = LibraryParams.NewExercise(subcategoryId, isFromWorkoutScreen)
            )
        } else {
            router.navigate(
                screen = Screen.FROM_LIBRARY_TO_CREATE_NEW_EXERCISE,
                data = LibraryParams.NewExercise(subcategoryId, isFromWorkoutScreen)
            )
        }
    }

    private fun deleteExercise(exerciseId: Int, subcategoryId: Int) {
        repo.deleteExercise(exerciseId)
            .observeUi()
            .doOnError { postStateEvent(LibraryListEvent.ExceptionResult(it as Exception)) }
            .doOnSubscribe { postStateEvent(LibraryListState.Loading) }
            .doAfterSuccess { getExercises(subcategoryId) }
            .subscribe()
    }

    private fun editStateAction(action: EditState.Action) {
        val event = when (action) {
            EditState.Action.DeselectAll -> LibraryListEvent.DeselectAllWorkouts
            EditState.Action.Hide -> LibraryListEvent.HideEditState
            EditState.Action.SelectAll -> LibraryListEvent.SelectAllWorkouts
            EditState.Action.Show -> LibraryListEvent.ShowEditState
            EditState.Action.DeleteSelected -> LibraryListEvent.DeleteSelectedWorkouts
        }
        mEvent.postValue(event)
    }
    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }
}
