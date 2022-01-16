package com.lefarmico.workout

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.repository.manager.*
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.SetParameterParams
import com.lefarmico.navigation.screen.Screen
import com.lefarmico.workout.WorkoutIntent.*
import com.lefarmico.workout.interactor.DateTimeHelper
import com.lefarmico.workout.interactor.ExerciseHelper
import com.lefarmico.workout.interactor.NavigateHelper
import com.lefarmico.workout.interactor.WorkoutHelper
import com.lefarmico.workout_notification.WorkoutRemindNotificationHelper
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class WorkoutViewModel @Inject constructor() : BaseViewModel<
    WorkoutIntent, WorkoutState, WorkoutEvent
    >() {

    @Inject lateinit var recordsRepository: WorkoutRecordsRepository
    @Inject lateinit var libraryRepository: LibraryRepository
    @Inject lateinit var workoutRepository: CurrentWorkoutRepository

    @Inject lateinit var dateManager: DateManager
    @Inject lateinit var formatterManager: FormatterManager
    @Inject lateinit var timeScheduleManager: TimeScheduleManager
    @Inject lateinit var workoutTitleManager: WorkoutTitleManager
    @Inject lateinit var formatterTimeManager: FormatterTimeManager
    @Inject lateinit var notificationHelper: WorkoutRemindNotificationHelper

    @Inject lateinit var router: Router

    private val exerciseHelper by lazy { ExerciseHelper(workoutRepository, libraryRepository) }
    private val navigateHelper by lazy { NavigateHelper(router) }
    private val dateTimeHelper by lazy {
        DateTimeHelper(
            dateManager, timeScheduleManager, formatterManager, formatterTimeManager
        )
    }
    private val workoutHelper by lazy {
        WorkoutHelper(
            recordsRepository, workoutRepository, dateManager,
            timeScheduleManager, workoutTitleManager
        )
    }

    private val switchObject = BehaviorSubject.create<Boolean>()
    private val switchStateObservable: Observable<Boolean>

    init {
        switchObject.onNext(false)
        switchStateObservable = scheduleTimeSwitcher().apply { subscribe() }
        mState.value = WorkoutState.SwitchState(false)
    }

    private fun addExercise(id: Int) {
        exerciseHelper.addExercise(id) { event -> mEvent.postValue(event) }
            .observeUi()
            .doAfterSuccess { state -> mState.value = state }
            .subscribe()
    }

    private fun deleteExercise(exerciseId: Int) {
        exerciseHelper.deleteExercise(exerciseId)
            .observeUi()
            .doAfterSuccess { state -> mState.value = state }
            .subscribe()
    }

    private fun getExercises() {
        exerciseHelper.getAllExercises()
            .observeUi()
            .doAfterSuccess { state -> mState.value = state }
            .subscribe()
    }

    private fun addSetToExercise(params: SetParameterParams) {
        exerciseHelper.addSetToExercise(params)
            .observeUi()
            .doAfterSuccess { state -> mState.value = state }
            .subscribe()
    }

    private fun deleteLastSet(exerciseId: Int) {
        exerciseHelper.deleteLastSet(exerciseId)
            .observeUi()
            .doAfterSuccess { state -> mState.value = state }
            .subscribe()
    }

    private fun finishWorkout() {
        editStateAction(EditState.Action.Hide)
        workoutHelper.finishWorkout()
            .observeUi()
            .doAfterSuccess { quad ->
                if (quad.fourth.isEmpty()) {
                    clear()
                    return@doAfterSuccess
                }
                switchObject.subscribe { switchState ->
                    val time = if (switchState) { quad.second } else { null }
                    saveWorkout(quad.third, quad.first, time, quad.fourth)
                }.dispose()
            }.subscribe()
    }

    private fun saveWorkout(
        title: String,
        date: LocalDate,
        time: LocalTime?,
        exercises: List<CurrentWorkoutDto.ExerciseWithSets>
    ) {
        workoutHelper.saveWorkout(title, date, time, exercises)
            .observeUi()
            .doAfterSuccess { state -> mState.value = state }
            .subscribe()
    }

    private fun clear(workoutId: Int) {
        recordsRepository.getWorkoutWithExerciseAnsSets(workoutId)
            .observeUi()
            .doAfterSuccess { dataState ->
                if (dataState is DataState.Success &&
                    dataState.data.workout.time != null
                ) {
                    val workoutDto = dataState.reduceWorkoutDto()
                    notificationHelper.startWorkoutReminderEvent(workoutDto)
                }
                clear()
            }.subscribe()
    }

    private fun clear() {
        workoutRepository.clearCache()
        timeScheduleManager.clearCache()
        workoutTitleManager.clearCache()
        switchObject.onNext(false)
        router.navigate(Screen.HOME_SCREEN)
    }

    private fun navigateToCategoryScreen() {
        navigateHelper.navigateToCategoryScreen { editStateAction(EditState.Action.Hide) }
    }

    private fun navigateToExerciseInfo(exerciseId: Int) {
        navigateHelper.navigateToExerciseInfo(exerciseId) { editStateAction(EditState.Action.Hide) }
    }

    private fun showCalendarPickerDialog() {
        dateManager.getSelectedDate()
            .observeUi()
            .doAfterSuccess { dataState ->
                val data = (dataState as DataState.Success).data
                navigateHelper.startCalendarPickerDialog(data) { date -> setWorkoutDate(date) }
            }.subscribe()
    }

    private fun showWorkoutTitleDialog() {
        workoutTitleManager.getTitle()
            .observeUi()
            .doAfterSuccess { title ->
                navigateHelper.startWorkoutTitleDialog(title) { newTitle ->
                    setWorkoutTitle(newTitle)
                }
            }.subscribe()
    }

    private fun showSetParameterDialog(exerciseId: Int) {
        navigateHelper.startSetParameterDialog(exerciseId) { params -> addSetToExercise(params) }
    }

    private fun showTimePickerDialog() {
        val localTime = LocalTime.now()
        navigateHelper.startTimePickerDialog(localTime) { time -> setWorkoutTime(time) }
    }

    private fun setWorkoutDate(localDate: LocalDate) {
        dateTimeHelper.setDate(localDate)
            .observeUi()
            .doAfterSuccess { state -> mState.value = state }
            .subscribe()
    }

    private fun getWorkoutDate() {
        dateTimeHelper.getDate()
            .observeUi()
            .doAfterSuccess { state -> mState.value = state }
            .subscribe()
    }

    private fun getWorkoutTime() {
        dateTimeHelper.getTime()
            .observeUi()
            .doAfterSuccess { state -> mState.value = state }
            .subscribe()
    }

    private fun setWorkoutTime(localTime: LocalTime) {
        dateTimeHelper.setTime(localTime)
            .observeUi()
            .doAfterSuccess { state -> mState.value = state }
            .subscribe()
    }

    private fun editStateAction(action: EditState.Action) {
        val event = when (action) {
            EditState.Action.DeleteSelected -> WorkoutEvent.HideEditState
            EditState.Action.DeselectAll -> WorkoutEvent.DeselectAllExercises
            EditState.Action.Hide -> WorkoutEvent.HideEditState
            EditState.Action.SelectAll -> WorkoutEvent.SelectAllExercises
            EditState.Action.Show -> WorkoutEvent.ShowEditState
        }
        mEvent.postValue(event)
    }

    private fun setWorkoutTitle(title: String) {
        workoutTitleManager.setTitle(title)
            .observeUi()
            .doAfterSuccess { newTitle -> mState.postValue(WorkoutState.TitleResult(newTitle)) }
            .subscribe()
    }

    private fun getWorkoutTitle() {
        workoutTitleManager.getTitle()
            .observeUi()
            .doAfterSuccess { title -> mState.postValue(WorkoutState.TitleResult(title)) }
            .subscribe()
    }

    // TODO почему не срабатывает после второго включения?
    private fun scheduleTimeSwitcher(): Observable<Boolean> {
        return Observable.create<Boolean> { input -> switchObject.subscribe { input.onNext(it) } }
            .doOnNext { switchMode ->
                when (switchMode) {
                    true -> getWorkoutTime()
                    false -> mState.value = WorkoutState.TimeResult("")
                }
            }
    }

    override fun triggerIntent(intent: WorkoutIntent) {
        when (intent) {
            is EditState -> editStateAction(intent.action)
            is AddSetToExercise -> addSetToExercise(intent.params)
            is AddExercise -> addExercise(intent.id)
            is DeleteExercise -> deleteExercise(intent.id)
            is DeleteLastSet -> deleteLastSet(intent.exerciseId)
            FinishWorkout -> finishWorkout()
            GetExercises -> getExercises()
            GetSelectedDate -> getWorkoutDate()
            GoToCategoryScreen -> navigateToCategoryScreen()
            is GoToExerciseInfo -> navigateToExerciseInfo(intent.libraryId)
            is ShowToast -> navigateHelper.showToast(intent.text)
            GetTitle -> getWorkoutTitle()
            is SwitchTimeScheduler -> switchObject.onNext(intent.isOn)

            StartTimePickerDialog -> showTimePickerDialog()
            StartCalendarPickerDialog -> showCalendarPickerDialog()
            is StartSetParameterDialog -> showSetParameterDialog(intent.exerciseId)
            StartWorkoutTitleDialog -> showWorkoutTitleDialog()
            is CloseWorkout -> clear(intent.workoutId)
            GetTime -> switchObject.subscribe()
        }
    }
}
