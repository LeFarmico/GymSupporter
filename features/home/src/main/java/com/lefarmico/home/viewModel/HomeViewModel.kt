package com.lefarmico.home.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.CalendarItemViewData
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toViewData
import com.lefarmico.core.mapper.toViewDataWorkoutWithExAndSets
import com.lefarmico.core.utils.SingleLiveEvent
import com.lefarmico.domain.repository.DateTimeManager
import com.lefarmico.domain.repository.FormatterManager
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.domain.utils.map
import com.lefarmico.home.intent.HomeEvents
import com.lefarmico.home.intent.HomeIntent
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.RecordMenuParams
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.screen.Screen
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel<HomeIntent>() {

    @Inject
    lateinit var repo: WorkoutRecordsRepository
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var dateTimeRepo: DateTimeManager
    @Inject
    lateinit var formatterManager: FormatterManager

    val workoutRecordsLiveData = MutableLiveData<DataState<List<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>>>()
    val actionBarLiveData = SingleLiveEvent<HomeEvents>()
    val calendarLiveData = MutableLiveData<DataState<List<CalendarItemViewData>>>()
    val monthAndYearLiveData = MutableLiveData<DataState<String>>()

    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")

    private fun getAllWorkoutRecords() {
        repo.getWorkoutsWithExerciseAnsSets()
            .observeUi()
            .doAfterSuccess { dataState ->
                val viewDataState = dataState.map { it.toViewDataWorkoutWithExAndSets() }
                workoutRecordsLiveData.value = viewDataState
            }.subscribe()
    }

    private fun navigateToWorkout() {
        actionBarLiveData.postValue(HomeEvents.HideEditState)
        router.navigate(
            screen = Screen.WORKOUT_SCREEN,
            data = WorkoutScreenParams.Empty
        )
    }

    private fun navigateToDetailsWorkout(workoutId: Int) {
        actionBarLiveData.postValue(HomeEvents.HideEditState)
        router.navigate(
            screen = Screen.EDIT_WORKOUT_RECORD_SCREEN,
            data = RecordMenuParams.WorkoutRecord(workoutId)
        )
    }

    private fun removeWorkout(workoutId: Int) {
        repo.deleteWorkoutWithExAndSets(workoutId)
            .observeUi()
            .doAfterSuccess {
                getWorkoutRecordsByDate()
                getMonthDates()
            }
            .subscribe()
    }

    private fun actionBarEvent(homeEvents: HomeEvents) {
        actionBarLiveData.postValue(homeEvents)
    }

    private fun getMonthDates() {
        dateTimeRepo.getCurrentDaysInMonth()
            .observeUi()
            .doAfterSuccess { dataState ->
                val viewDataState = dataState.map { it.toViewData() }
                calendarLiveData.postValue(viewDataState)
            }.subscribe()
    }

    private fun getWorkoutRecordsByDate() {
        dateTimeRepo.getSelectedDate()
            .observeUi()
            .flatMap {
                // Всегда Success
                val data = (it as DataState.Success).data
                repo.getWorkoutWithExerciseAndSetsByDate(data).observeUi()
            }.doAfterSuccess { dataState ->
                val viewDataState = dataState.map { it.toViewDataWorkoutWithExAndSets() }
                workoutRecordsLiveData.value = viewDataState
            }.subscribe()
    }

    private fun getCurrentMonth() {
        dateTimeRepo.currentMonth()
            .observeUi()
            .doAfterSuccess { dataState ->
                monthAndYearLiveData.value = dataState.map { it.format(formatter) }
                getMonthDates()
            }.subscribe()
    }

    private fun nextMonth() {
        dateTimeRepo.nextMonth()
            .observeUi()
            .doAfterSuccess { dataState ->
                monthAndYearLiveData.value = dataState.map { it.format(formatter) }
                getMonthDates()
            }.subscribe()
    }
    private fun prevMonth() {
        dateTimeRepo.prevMonth()
            .observeUi()
            .doAfterSuccess { dataState ->
                monthAndYearLiveData.value = dataState.map { it.format(formatter) }
                getMonthDates()
            }.subscribe()
    }

    private fun setWorkoutRecordsByDate(localDate: LocalDate) {
        dateTimeRepo.selectDate(localDate)
            .observeUi()
            .doAfterSuccess { getWorkoutRecordsByDate() }
            .subscribe()
    }

    override fun onTriggerEvent(eventType: HomeIntent) {
        when (eventType) {
            HomeIntent.GetWorkoutRecords -> getAllWorkoutRecords()
            HomeIntent.NavigateToWorkout -> navigateToWorkout()
            is HomeIntent.NavigateToDetailsWorkout -> navigateToDetailsWorkout(eventType.workoutId)
            is HomeIntent.RemoveWorkout -> removeWorkout(eventType.workoutId)
            is HomeIntent.ScreenEvent -> actionBarEvent(eventType.event)
            is HomeIntent.GetCurrentDaysInMonth -> getMonthDates()
            is HomeIntent.GetWorkoutRecordsByCurrentDate -> getWorkoutRecordsByDate()
            is HomeIntent.GetCurrentMonth -> getCurrentMonth()
            HomeIntent.GetNextMonth -> nextMonth()
            HomeIntent.GetPrevMonth -> prevMonth()
            is HomeIntent.SetClickedDate -> setWorkoutRecordsByDate(eventType.localDate)
        }
    }
}
