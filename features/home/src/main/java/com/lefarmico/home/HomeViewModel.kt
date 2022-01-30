package com.lefarmico.home

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toViewData
import com.lefarmico.domain.loaders.MuscleCategoryLoader
import com.lefarmico.domain.preferences.FirstLaunchPreferenceHelper
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.repository.manager.DateManager
import com.lefarmico.domain.repository.manager.FormatterManager
import com.lefarmico.domain.repository.manager.FormatterMonthManager
import com.lefarmico.domain.repository.manager.FormatterTimeManager
import com.lefarmico.domain.utils.DataState
import com.lefarmico.domain.utils.map
import com.lefarmico.home.HomeIntent.EditState.Action.*
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.RecordMenuParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.screen.Screen
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repo: WorkoutRecordsRepository,
    private val router: Router,
    private val dateManager: DateManager,
    private val formatterMonthManager: FormatterMonthManager,
    private val formatterManager: FormatterManager,
    private val formatterTimeManager: FormatterTimeManager,
) :
    BaseViewModel<HomeIntent, HomeState, HomeEvent>() {

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
            is HomeIntent.ShowToast -> showToast(intent.text)
        }
    }

    private fun postStateEvent(value: BaseState) {
        when (value) {
            is BaseState.Event -> mEvent.postValue(value as HomeEvent)
            is BaseState.State -> mState.value = value as HomeState
        }
    }

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
            .doOnSubscribe { postStateEvent(HomeState.Loading) }
            .doOnError { postStateEvent(HomeEvent.ExceptionResult(it as Exception)) }
            .doAfterSuccess { getWorkoutRecords() }
            .subscribe()
    }

    private fun getWorkoutRecords() {
        dateManager.getSelectedDate()
            .observeUi()
            .doOnSubscribe { postStateEvent(HomeState.Loading) }
            .doOnError { postStateEvent(HomeEvent.ExceptionResult(it as Exception)) }
            .flatMap { dataState ->
                repo.getWorkoutWithExerciseAndSetsByDate(dataState.resolve()).observeUi()
            }.doAfterSuccess { dataState ->
                getSelectedFormatterListener { dateF, timeF -> postStateEvent(dataState.reduce(dateF, timeF)) }
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
                    postStateEvent(dataState.map { it.format(formatter) }.reduce())
                    getDates()
                }.subscribe()
        }
    }

    private fun removeWorkout(workoutId: Int) {
        repo.deleteWorkoutWithExAndSets(workoutId)
            .observeUi()
            .doOnSubscribe { postStateEvent(HomeState.Loading) }
            .doOnError { postStateEvent(HomeEvent.ExceptionResult(it as Exception)) }
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
            .doOnError { postStateEvent(HomeEvent.ExceptionResult(it as Exception)) }
            .zipWith(formatterTimeManager.getSelectedTimeFormatter()) { dateF, timeF -> Pair(dateF, timeF) }
            .doAfterSuccess { pair -> formatter(pair.first.formatter, pair.second.formatter) }
            .subscribe()
    }

    private fun currentMonthFormatterListener(formatter: (DateTimeFormatter) -> Unit) {
        formatterMonthManager.getSelectedMonthFormatter()
            .observeUi()
            .doOnError { postStateEvent(HomeEvent.ExceptionResult(it as Exception)) }
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

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }
}
