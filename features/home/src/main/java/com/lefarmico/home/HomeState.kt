package com.lefarmico.home

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.entity.CalendarItemViewData
import com.lefarmico.core.entity.WorkoutRecordsViewData
import java.lang.Exception

sealed class HomeState : BaseState.State {
    data class WorkoutResult(val workoutList: List<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>) : HomeState()
    data class CalendarResult(val calendarItemList: List<CalendarItemViewData>) : HomeState()
    data class MonthAndYearResult(val monthAndYearText: MonthAndYearText) : HomeState()
    object Loading : HomeState()
    data class ExceptionResult(val exception: Exception) : HomeState()
}