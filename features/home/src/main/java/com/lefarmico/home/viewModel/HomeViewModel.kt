package com.lefarmico.home.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.CalendarItemViewData
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toViewData
import com.lefarmico.core.mapper.toViewDataWorkoutWithExAndSets
import com.lefarmico.core.toolbar.EditActionBarEvents
import com.lefarmico.core.utils.SingleLiveEvent
import com.lefarmico.domain.repository.CalendarRepository
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.domain.utils.map
import com.lefarmico.home.intent.HomeIntent
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.RecordMenuParams
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.screen.Screen
import java.time.LocalDateTime
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel<HomeIntent>() {

    @Inject
    lateinit var repo: WorkoutRecordsRepository
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var calendarRepo: CalendarRepository

    val workoutRecordsLiveData = MutableLiveData<DataState<List<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>>>()
    val actionBarLiveData = SingleLiveEvent<EditActionBarEvents>()
    val calendarLiveData = MutableLiveData<DataState<List<CalendarItemViewData>>>()
    val monthAndYearLiveData = MutableLiveData<DataState<String>>()

    private fun getWorkoutRecords() {
        repo.getWorkoutsWithExerciseAnsSets()
            .observeUi()
            .doAfterSuccess { dataState ->
                val viewDataState = dataState.map { it.toViewDataWorkoutWithExAndSets() }
                workoutRecordsLiveData.value = viewDataState
            }.subscribe()
    }

    private fun navigateToWorkout() {
        actionBarLiveData.postValue(EditActionBarEvents.Close)
        router.navigate(
            screen = Screen.WORKOUT_SCREEN,
            data = WorkoutScreenParams.Empty
        )
    }

    private fun navigateToDetailsWorkout(workoutId: Int) {
        actionBarLiveData.postValue(EditActionBarEvents.Close)
        router.navigate(
            screen = Screen.EDIT_WORKOUT_RECORD_SCREEN,
            data = RecordMenuParams.WorkoutRecord(workoutId)
        )
    }

    private fun removeWorkout(workoutId: Int) {
        repo.deleteWorkoutWithExAndSets(workoutId)
            .observeUi()
            .doAfterSuccess { getWorkoutRecords() }
            .subscribe()
    }

    private fun actionBarEvent(actionBarEvent: EditActionBarEvents) {
        actionBarLiveData.postValue(actionBarEvent)
    }

    private fun getMonthDates(date: LocalDateTime) {
        calendarRepo.getDaysByDate(date)
            .observeUi()
            .doAfterSuccess { dataState ->
                val viewDataState = dataState.map { it.toViewData() }
                calendarLiveData.postValue(viewDataState)
            }.subscribe()
    }

    private fun getWorkoutRecordsByDate(date: LocalDateTime) {
        repo.getWorkoutWithExerciseAndSetsByDate(date)
            .observeUi()
            .doAfterSuccess { dataState ->
                val viewDataState = dataState.map { it.toViewDataWorkoutWithExAndSets() }
                workoutRecordsLiveData.value = viewDataState
            }.subscribe()
    }

    private fun getMonthAndYear(date: LocalDateTime) {
        calendarRepo.getCurrentMonthAndYear(date)
            .observeUi()
            .doAfterSuccess { dataState ->
                monthAndYearLiveData.value = dataState
            }.subscribe()
    }

    override fun onTriggerEvent(eventType: HomeIntent) {
        when (eventType) {
            HomeIntent.GetWorkoutRecords -> getWorkoutRecords()
            HomeIntent.NavigateToWorkout -> navigateToWorkout()
            is HomeIntent.NavigateToDetailsWorkout -> navigateToDetailsWorkout(eventType.workoutId)
            is HomeIntent.RemoveWorkout -> removeWorkout(eventType.workoutId)
            is HomeIntent.ActionBarEvent -> actionBarEvent(eventType.event)
            is HomeIntent.GetCalendarDates -> getMonthDates(eventType.date)
            is HomeIntent.GetWorkoutRecordsByDate -> getWorkoutRecordsByDate(eventType.date)
            is HomeIntent.GetMonthAndYearByDate -> getMonthAndYear(eventType.date)
        }
    }
}
