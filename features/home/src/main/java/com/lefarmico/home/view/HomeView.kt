package com.lefarmico.home.view

import com.lefarmico.core.entity.CalendarItemViewData
import com.lefarmico.core.entity.WorkoutRecordsViewData

interface HomeView {

    fun showLoading()

    fun showSuccess()

    fun showError(e: Exception)

    fun showEmpty()

    fun showWorkouts(items: List<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>)

    fun hideWorkouts()

    fun showCalendar(items: List<CalendarItemViewData>)

    fun showCurrentMonthAndYear(month: String)

    fun showEditState()

    fun hideEditState()
}
