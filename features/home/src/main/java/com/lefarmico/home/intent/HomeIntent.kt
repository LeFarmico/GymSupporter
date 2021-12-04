package com.lefarmico.home.intent

import com.lefarmico.core.base.BaseIntent
import java.time.LocalDateTime

sealed class HomeIntent : BaseIntent() {

    object GetWorkoutRecords : HomeIntent()

    object NavigateToWorkout : HomeIntent()

    data class NavigateToDetailsWorkout(val workoutId: Int) : HomeIntent()

    data class RemoveWorkout(val workoutId: Int) : HomeIntent()

    data class ScreenEvent(val event: HomeEvents) : HomeIntent()

    data class GetCalendarDates(val date: LocalDateTime) : HomeIntent()

    data class GetWorkoutRecordsByDate(val date: LocalDateTime) : HomeIntent()

    data class GetMonthAndYearByDate(val date: LocalDateTime) : HomeIntent()
}
