package com.lefarmico.home

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.repository.manager.DateManager
import com.lefarmico.domain.repository.manager.FormatterManager
import com.lefarmico.domain.repository.manager.FormatterMonthManager
import com.lefarmico.domain.repository.manager.FormatterTimeManager
import com.lefarmico.domain.utils.map
import com.lefarmico.home.HomeIntent.EditState.Action.*
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.RecordMenuParams
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.screen.Screen
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repo: WorkoutRecordsRepository,
    private val router: Router,
    private val dateRepo: DateManager,
    private val formatterMonthManager: FormatterMonthManager,
    private val formatterManager: FormatterManager,
    private val formatterTimeManager: FormatterTimeManager
) :
    BaseViewModel<HomeIntent, HomeState, HomeEvent>() {

    private fun navigateToWorkout() {
        dispatchIntent(HomeIntent.EditState(Hide))
        router.navigate(
            screen = Screen.WORKOUT_SCREEN,
            data = WorkoutScreenParams.Empty
        )
    }

    private fun navigateToDetailsWorkout(workoutId: Int) {
        dispatchIntent(HomeIntent.EditState(Hide))
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
        dateRepo.getCurrentDaysInMonth()
            .observeUi()
            .doAfterSuccess { dataState -> mState.value = dataState.reduce() }
            .subscribe()
    }

    private fun getWorkoutRecordsByDate() {
        dateRepo.getSelectedDate()
            .observeUi()
            .flatMap { dataState ->
                repo.getWorkoutWithExerciseAndSetsByDate(dataState.resolve()).observeUi()
            }.doAfterSuccess { dataState ->
                getSelectedFormatters { dateF, timeF -> mState.value = dataState.reduce(dateF, timeF) }
            }.subscribe()
    }

    private fun changeMonth(month: HomeIntent.ChangeMonth.Change) {
        val dateObservable = when (month) {
            HomeIntent.ChangeMonth.Change.Current -> dateRepo.currentMonth()
            HomeIntent.ChangeMonth.Change.Next -> dateRepo.nextMonth()
            HomeIntent.ChangeMonth.Change.Prev -> dateRepo.prevMonth()
        }
        currentMonthFormatterListener { formatter ->
            dateObservable
                .observeUi()
                .doAfterSuccess { dataState ->
                    mState.value = dataState.map { it.format(formatter) }.reduce()
                    getMonthDates()
                }.subscribe()
        }
    }

    private fun editStateAction(action: HomeIntent.EditState.Action) {
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
        dateRepo.selectDate(localDate)
            .observeUi()
            .doAfterSuccess { getWorkoutRecordsByDate() }
            .subscribe()
    }

    private fun getSelectedFormatters(formatter: (DateTimeFormatter, DateTimeFormatter) -> Unit) {
        formatterManager.getSelectedFormatter()
            .observeUi()
            .zipWith(formatterTimeManager.getSelectedTimeFormatter()) { dateF, timeF ->

                Pair(dateF, timeF)
            }.doAfterSuccess { pair ->
                formatter(pair.first.formatter, pair.second.formatter)
            }
            .subscribe()
    }

    private fun currentMonthFormatterListener(formatter: (DateTimeFormatter) -> Unit) {
        formatterMonthManager.getSelectedMonthFormatter()
            .observeUi()
            .doAfterSuccess { dto -> formatter(dto.formatter) }
            .subscribe()
    }

    override fun triggerIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ClickDate -> setWorkoutRecordsByDate(intent.localDate)
            is HomeIntent.DeleteWorkout -> removeWorkout(intent.workoutId)
            is HomeIntent.ChangeMonth -> changeMonth(intent.change)
            HomeIntent.GetDaysInMonth -> getMonthDates()
            HomeIntent.GetWorkoutRecordsByCurrentDate -> getWorkoutRecordsByDate()
            is HomeIntent.NavigateToDetailsWorkoutScreen -> navigateToDetailsWorkout(intent.workoutId)
            HomeIntent.NavigateToWorkoutScreen -> navigateToWorkout()
            is HomeIntent.EditState -> editStateAction(intent.action)
        }
    }
}
