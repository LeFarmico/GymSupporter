package com.lefarmico.home

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toViewData
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.repository.manager.DateManager
import com.lefarmico.domain.repository.manager.FormatterManager
import com.lefarmico.domain.repository.manager.FormatterMonthManager
import com.lefarmico.domain.repository.manager.FormatterTimeManager
import com.lefarmico.domain.utils.DataState
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
    private val dateManager: DateManager,
    private val formatterMonthManager: FormatterMonthManager,
    private val formatterManager: FormatterManager,
    private val formatterTimeManager: FormatterTimeManager
) :
    BaseViewModel<HomeIntent, HomeState, HomeEvent>() {

    private fun getDates() {
        dateManager.getCurrentDaysInMonth()
            .observeUi()
            .zipWith(dateManager.getSelectedDate()) { dates, selDate ->
                require(dates is DataState.Success)
                require(selDate is DataState.Success)
                Pair(dates.data, selDate.data)
            }.doAfterSuccess { pairDates ->
                mState.value = HomeState.CalendarResult(pairDates.first.toViewData(), pairDates.second)
            }.subscribe()
    }

    private fun getWorkoutRecordsByDate(localDate: LocalDate) {
        dateManager.selectDate(localDate)
            .observeUi()
            .doAfterSuccess { getWorkoutRecords() }
            .subscribe()
    }

    private fun getWorkoutRecords() {
        dateManager.getSelectedDate()
            .observeUi()
            .flatMap { dataState ->
                repo.getWorkoutWithExerciseAndSetsByDate(dataState.resolve()).observeUi()
            }.doAfterSuccess { dataState ->
                getSelectedFormatterListener { dateF, timeF -> mState.value = dataState.reduce(dateF, timeF) }
            }.subscribe()
    }

    private fun getMonth(month: HomeIntent.ChangeMonth.Change) {
        val dateObservable = when (month) {
            HomeIntent.ChangeMonth.Change.Current -> dateManager.currentMonth()
            HomeIntent.ChangeMonth.Change.Next -> dateManager.nextMonth()
            HomeIntent.ChangeMonth.Change.Prev -> dateManager.prevMonth()
        }
        currentMonthFormatterListener { formatter ->
            dateObservable
                .observeUi()
                .doAfterSuccess { dataState ->
                    mState.value = dataState.map { it.format(formatter) }.reduce()
                    getDates()
                }.subscribe()
        }
    }

    private fun removeWorkout(workoutId: Int) {
        repo.deleteWorkoutWithExAndSets(workoutId)
            .observeUi()
            .doAfterSuccess {
                getWorkoutRecords()
                getDates()
            }
            .subscribe()
    }

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

    private fun getSelectedFormatterListener(formatter: (DateTimeFormatter, DateTimeFormatter) -> Unit) {
        formatterManager.getSelectedFormatter()
            .observeUi()
            .zipWith(formatterTimeManager.getSelectedTimeFormatter()) { dateF, timeF -> Pair(dateF, timeF) }
            .doAfterSuccess { pair -> formatter(pair.first.formatter, pair.second.formatter) }
            .subscribe()
    }

    private fun currentMonthFormatterListener(formatter: (DateTimeFormatter) -> Unit) {
        formatterMonthManager.getSelectedMonthFormatter()
            .observeUi()
            .doAfterSuccess { dto -> formatter(dto.formatter) }
            .subscribe()
    }
    private fun backToCurrentDate() {
        dateManager.selectMonth(LocalDate.now())
            .observeUi()
            .flatMap { dateManager.selectDate(LocalDate.now()) }
            .doAfterSuccess {
                getDates()
                getMonth(HomeIntent.ChangeMonth.Change.Current)
                getWorkoutRecords()
            }.subscribe()
    }

    override fun triggerIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ClickDate -> getWorkoutRecordsByDate(intent.localDate)
            is HomeIntent.DeleteWorkout -> removeWorkout(intent.workoutId)
            is HomeIntent.ChangeMonth -> getMonth(intent.change)
            HomeIntent.GetDaysInMonth -> getDates()
            HomeIntent.GetWorkoutRecordsByCurrentDate -> getWorkoutRecords()
            is HomeIntent.NavigateToDetailsWorkoutScreen -> navigateToDetailsWorkout(intent.workoutId)
            HomeIntent.NavigateToWorkoutScreen -> navigateToWorkout()
            is HomeIntent.EditState -> editStateAction(intent.action)
            HomeIntent.BackToCurrentDate -> backToCurrentDate()
        }
    }
}
