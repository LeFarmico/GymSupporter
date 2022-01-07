package com.lefarmico.home

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.DateTimeManager
import com.lefarmico.domain.repository.FormatterManager
import com.lefarmico.domain.repository.FormatterMonthManager
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.map
import com.lefarmico.home.HomeAction.ChangeMonth.Change.*
import com.lefarmico.home.HomeAction.EditState.Action.*
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.RecordMenuParams
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.screen.Screen
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeViewModel @Inject constructor() :
    BaseViewModel<HomeIntent, HomeAction, HomeState, HomeEvent>() {

    @Inject lateinit var repo: WorkoutRecordsRepository
    @Inject lateinit var router: Router
    @Inject lateinit var dateTimeRepo: DateTimeManager
    @Inject lateinit var formatterMonthManager: FormatterMonthManager
    @Inject lateinit var formatterManager: FormatterManager

    private fun navigateToWorkout() {
        dispatchIntent(HomeIntent.HideEditState)
        router.navigate(
            screen = Screen.WORKOUT_SCREEN,
            data = WorkoutScreenParams.Empty
        )
    }

    private fun navigateToDetailsWorkout(workoutId: Int) {
        dispatchIntent(HomeIntent.HideEditState)
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

    private fun getMonthDates() {
        dateTimeRepo.getCurrentDaysInMonth()
            .observeUi()
            .doAfterSuccess { dataState -> mState.value = dataState.reduce() }
            .subscribe()
    }

    private fun getWorkoutRecordsByDate() {
        dateTimeRepo.getSelectedDate()
            .observeUi()
            .flatMap { dataState ->
                repo.getWorkoutWithExerciseAndSetsByDate(dataState.resolve()).observeUi()
            }.doAfterSuccess { dataState ->
                getSelectedFormatter { formatter -> mState.value = dataState.reduce(formatter) }
            }.subscribe()
    }

    private fun changeMonth(month: HomeAction.ChangeMonth.Change) {
        val dateObservable = when (month) {
            Current -> dateTimeRepo.currentMonth()
            Next -> dateTimeRepo.nextMonth()
            Prev -> dateTimeRepo.prevMonth()
        }
        currentMonthFormatterListener { formatter ->
            dateObservable.observeUi()
                .doAfterSuccess { dataState ->
                    mState.value = dataState.map { it.format(formatter) }.reduce()
                }.subscribe()
        }
    }

    private fun editStateAction(action: HomeAction.EditState.Action) {
        val event = when (action) {
            DeselectAll -> HomeEvent.DeselectAllWorkouts
            Hide -> HomeEvent.HideEditState
            SelectAll -> HomeEvent.SelectAllWorkouts
            Show -> HomeEvent.ShowEditState
            DeleteSelected -> HomeEvent.DeleteSelectedWorkouts
        }
        mEvent.postValue(event)
    }

    private fun setWorkoutRecordsByDate(localDate: LocalDate) {
        dateTimeRepo.selectDate(localDate)
            .observeUi()
            .doAfterSuccess { getWorkoutRecordsByDate() }
            .subscribe()
    }

    private fun getSelectedFormatter(formatter: (DateTimeFormatter) -> Unit) {
        formatterManager.getSelectedFormatter()
            .observeUi()
            .doAfterSuccess { formatterDto -> formatter(formatterDto.formatter) }
            .subscribe()
    }

    private fun currentMonthFormatterListener(formatter: (DateTimeFormatter) -> Unit) {
        formatterMonthManager.getSelectedMonthFormatter()
            .observeUi()
            .doAfterSuccess { dto -> formatter(dto.formatter) }
            .subscribe()
    }

    override fun triggerAction(action: HomeAction) {
        when (action) {
            // State
            is HomeAction.ClickDate -> setWorkoutRecordsByDate(action.localDate)
            is HomeAction.ChangeMonth -> changeMonth(action.change)
            is HomeAction.DeleteWorkout -> removeWorkout(action.workoutId)
            HomeAction.GetDaysInMonth -> getMonthDates()
            HomeAction.GetWorkoutRecordsByCurrentDate -> getWorkoutRecordsByDate()
            is HomeAction.NavigateToDetailsWorkoutScreen -> navigateToDetailsWorkout(action.workoutId)
            HomeAction.NavigateToWorkoutScreen -> navigateToWorkout()

            // Event
            is HomeAction.EditState -> editStateAction(action.action)
        }
    }

    override fun intentToAction(intent: HomeIntent): HomeAction {
        return when (intent) {
            // State
            is HomeIntent.ClickDate -> HomeAction.ClickDate(intent.localDate)
            is HomeIntent.DeleteWorkout -> HomeAction.DeleteWorkout(intent.workoutId)
            HomeIntent.GetDaysInMonth -> HomeAction.GetDaysInMonth
            HomeIntent.GetWorkoutRecordsByCurrentDate -> HomeAction.GetWorkoutRecordsByCurrentDate
            is HomeIntent.NavigateToDetailsWorkoutScreen -> HomeAction.NavigateToDetailsWorkoutScreen(intent.workoutId)
            HomeIntent.NavigateToWorkoutScreen -> HomeAction.NavigateToWorkoutScreen
            HomeIntent.CurrentMonth -> HomeAction.ChangeMonth(Current)
            HomeIntent.NextMonth -> HomeAction.ChangeMonth(Next)
            HomeIntent.PrevMonth -> HomeAction.ChangeMonth(Prev)

            // Event
            HomeIntent.ShowEditState -> HomeAction.EditState(Show)
            HomeIntent.HideEditState -> HomeAction.EditState(Hide)
            HomeIntent.DeleteSelectedWorkouts -> HomeAction.EditState(DeleteSelected)
            HomeIntent.SelectAllWorkouts -> HomeAction.EditState(SelectAll)
        }
    }
}
