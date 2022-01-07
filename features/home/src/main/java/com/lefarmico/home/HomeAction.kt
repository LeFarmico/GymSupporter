package com.lefarmico.home

import com.lefarmico.core.base.BaseAction
import java.time.LocalDate

sealed class HomeAction : BaseAction {
    object GetWorkoutRecordsByCurrentDate : HomeAction()
    object NavigateToWorkoutScreen : HomeAction()
    data class NavigateToDetailsWorkoutScreen(val workoutId: Int) : HomeAction()
    data class DeleteWorkout(val workoutId: Int) : HomeAction()

    data class ClickDate(val localDate: LocalDate) : HomeAction()
    object GetDaysInMonth : HomeAction()

    data class ChangeMonth(val change: Change) : HomeAction() {
        sealed class Change {
            object Next : Change()
            object Prev : Change()
            object Current : Change()
        }
    }

    data class EditState(val action: Action) : HomeAction() {
        sealed class Action {
            object Show : Action()
            object Hide : Action()
            object SelectAll : Action()
            object DeselectAll : Action()
            object DeleteSelected : Action()
        }
    }
}
