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
    object CurrentMonth : HomeIntent()
    object NextMonth : HomeIntent()
    object PrevMonth : HomeIntent()

    object ShowEditState : HomeIntent()
    object HideEditState : HomeIntent()
    object SelectAllWorkouts : HomeIntent()
    object DeleteSelectedWorkouts : HomeIntent()
}
