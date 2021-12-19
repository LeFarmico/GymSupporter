package com.lefarmico.home.intent

import com.lefarmico.core.base.BaseIntent
import java.time.LocalDate

sealed class HomeIntent : BaseIntent() {

    object GetWorkoutRecords : HomeIntent()

    object NavigateToWorkout : HomeIntent()

    data class NavigateToDetailsWorkout(val workoutId: Int) : HomeIntent()

    data class RemoveWorkout(val workoutId: Int) : HomeIntent()

    data class ScreenEvent(val event: HomeEvents) : HomeIntent()

    object GetWorkoutRecordsByCurrentDate : HomeIntent()

    object GetCurrentDaysInMonth : HomeIntent()

    object GetCurrentMonth : HomeIntent()

    object GetNextMonth : HomeIntent()

    object GetPrevMonth : HomeIntent()

    data class SetClickedDate(val localDate: LocalDate) : HomeIntent()
}
