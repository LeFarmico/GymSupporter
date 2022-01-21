package com.lefarmico.workout

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toCurrent
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

class WorkoutViewModel @Inject constructor(
    private val recordsRepository: WorkoutRecordsRepository,
    private val libraryRepository: LibraryRepository,
    private val workoutRepository: CurrentWorkoutRepository,
    private val dateManager: DateManager,
    private val formatterManager: FormatterManager,
    private val timeScheduleManager: TimeScheduleManager,
    private val workoutTitleManager: WorkoutTitleManager,
    private val formatterTimeManager: FormatterTimeManager,
    private val notificationHelper: WorkoutRemindNotificationHelper,
    private val router: Router
) : BaseViewModel<
    WorkoutIntent, WorkoutState, WorkoutEvent
    >() {

    private val exerciseHelper = ExerciseHelper(workoutRepository, libraryRepository)
    private val navigateHelper = NavigateHelper(router)
    private val dateTimeHelper = DateTimeHelper(
        dateManager, timeScheduleManager, formatterManager, formatterTimeManager
    )
    private val workoutHelper = WorkoutHelper(
        recordsRepository, workoutRepository, dateManager,
        timeScheduleManager, workoutTitleManager
    )

    private val switchObject = BehaviorSubject.create<Boolean>()
    private val switchStateObservable: Observable<Boolean>

    // def id value
    private var workoutRecordId = 0

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
                    save(quad.third, quad.first, time, quad.fourth, workoutRecordId)
                    workoutRecordId = 0
                }.dispose()
            }.subscribe()
    }

    private fun save(
        title: String,
        date: LocalDate,
        time: LocalTime?,
        exercises: List<CurrentWorkoutDto.ExerciseWithSets>,
        workoutRecordId: Int
    ) {
        workoutHelper.saveWorkout(title, date, time, exercises, workoutRecordId)
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
        timeScheduleManager.getTime()
            .observeUi()
            .doAfterSuccess { time ->
                require(time is DataState.Success)
                navigateHelper.startTimePickerDialog(time.data) { newTime -> setWorkoutTime(newTime) }
            }
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

    private fun loadWorkoutRecord(workoutRecordId: Int) {
        this.workoutRecordId = workoutRecordId
        recordsRepository.getWorkoutWithExerciseAnsSets(workoutRecordId)
            .observeUi()
            .flatMap { dataState ->
                require(dataState is DataState.Success)
                val exercises = dataState.data.exerciseWithSetsList.toCurrent()
                val workout = dataState.data.workout

                setWorkoutDate(workout.date)
                if (workout.time != null) { setWorkoutTime(workout.time!!) }
                setWorkoutTitle(workout.title)

                workoutRepository.addExercises(exercises)
            }.doAfterSuccess { getExercises() }
            .subscribe()
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
            is LoadWorkoutRecord -> loadWorkoutRecord(intent.workoutRecordId)
        }
    }
}
