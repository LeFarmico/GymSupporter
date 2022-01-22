package com.lefarmico.workout.interactor

import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.dialog.Dialog
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.SetParameterParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.screen.Screen
import java.lang.Exception
import java.time.LocalDate
import java.time.LocalTime

class NavigateHelper(
    private val router: Router,
    private val libraryRepository: LibraryRepository
) {

    fun startWorkoutTitleDialog(title: String, callback: (String) -> Unit) {
        val dialog = Dialog.FieldEditorDialog(title) {
            callback(it)
        }
        router.showDialog(dialog)
    }

    fun startSetParameterDialog(exerciseId: Int, callback: (SetParameterParams) -> Unit) {
        val dialog = Dialog.SetParameterPickerDialog(exerciseId) {
            callback(it)
        }
        router.showDialog(dialog)
    }

    fun startTimePickerDialog(localTime: LocalTime, callback: (LocalTime) -> Unit) {
        val dialog = Dialog.TimePickerDialog(localTime) {
            callback(it)
        }
        router.showDialog(dialog)
    }

    fun startCalendarPickerDialog(localDate: LocalDate, callback: (LocalDate) -> Unit) {
        val dialog = Dialog.CalendarPickerDialog(localDate) { clickedDate ->
            callback(clickedDate)
        }
        router.showDialog(dialog)
    }

    fun navigateToExerciseInfo(exerciseId: Int, action: () -> Unit) {
        action()
        libraryRepository.getExercise(exerciseId)
            .observeUi()
            .onErrorReturn { DataState.Error(it as Exception) }
            .doAfterSuccess {
                if (it is DataState.Error) {
                    showToast("Looks like this exercise is not exist")
                } else {
                    router.navigate(
                        screen = Screen.EXERCISE_DETAILS_SCREEN_FROM_WORKOUT,
                        data = LibraryParams.Exercise(exerciseId)
                    )
                }
            }.subscribe()
    }

    fun navigateToCategoryScreen(action: () -> Unit) {
        action()
        router.navigate(
            screen = Screen.CATEGORY_LIST_SCREEN,
            data = LibraryParams.CategoryList(true)
        )
    }

    fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }
}
