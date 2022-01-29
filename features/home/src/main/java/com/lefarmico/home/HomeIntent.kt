package com.lefarmico.home

import com.lefarmico.core.base.BaseIntent
import java.time.LocalDate

sealed class HomeIntent : BaseIntent {

    object GetWorkoutRecordsByCurrentDate : HomeIntent()
    object NavigateToWorkoutScreen : HomeIntent()
    data class NavigateToDetailsWorkoutScreen(val workoutId: Int) : HomeIntent()
    data class DeleteWorkout(val workoutId: Int) : HomeIntent()
    data class ClickDate(val localDate: LocalDate) : HomeIntent()
    object GetDaysInMonth : HomeIntent()

    data class ChangeMonth(val change: Change) : HomeIntent() {
        sealed class Change {
            object Next : Change()
            object Prev : Change()
            object Current : Change()
        }
    }

    data class EditState(val action: Action) : HomeIntent() {
        sealed class Action {
            object Show : Action()
            object Hide : Action()
            object SelectAll : Action()
            object DeselectAll : Action()
            object DeleteSelected : Action()
        }
    }

    object BackToCurrentDate : HomeIntent()
    object TryLoadDefaultData : HomeIntent()
}
