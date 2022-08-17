package com.lefarmico.workout

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeIO
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toCurrent
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.repository.manager.*
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.screen.Screen
import com.lefarmico.workout.WorkoutIntent.*
import com.lefarmico.workout.interactor.DateTimeHelper
import com.lefarmico.workout.interactor.ExerciseHelper
import com.lefarmico.workout.interactor.NavigateHelper
import com.lefarmico.workout.interactor.WorkoutHelper
import com.lefarmico.workout_notification.WorkoutRemindNotificationHelper
import java.lang.Exception
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
    private val navigateHelper = NavigateHelper(router, libraryRepository)
    private val dateTimeHelper = DateTimeHelper(
        dateManager, timeScheduleManager, formatterManager, formatterTimeManager
    )
    private val workoutHelper = WorkoutHelper(
        recordsRepository, workoutRepository, dateManager,
        timeScheduleManager, workoutTitleManager
    )

    // def id value
    private var workoutRecordId = 0
    private var switchState = false

    init {
        mState.value = WorkoutState.SwitchState(false)
    }

    override fun triggerIntent(intent: WorkoutIntent) {
        when (intent) {
            is CloseWorkout -> close(intent.workoutId)
            is EditState -> editStateAction(intent)
            is Navigate -> navigateAction(intent)
            is Title -> titleAction(intent)
            is Date -> dateAction(intent)
            is Exercise -> exerciseAction(intent)
            is Dialog -> dialogAction(intent)
            is Time -> timeAction(intent)
            is SwitchState -> switchAction(intent)
            is Workout -> workoutAction(intent)
            is ShowToast -> toast(intent.text)
            is ExSet -> setAction(intent)
            is UpdateModeIntent -> updateModeAction(intent)
        }
    }

    private fun updateModeAction(action: UpdateModeIntent) {
        when (action) {
            UpdateModeIntent.Get -> {
                workoutRepository.isUpdateMode()
                    .observeUi()
                    .doAfterSuccess { isUpdate ->
                        postEventState(WorkoutState.UpdateMode(isUpdate))
                    }
                    .subscribe()
            }
            is UpdateModeIntent.Set -> {
                workoutRepository.setUpdate(action.isUpdateMode)
                    .observeUi()
                    .doAfterSuccess { isUpdate -> postEventState(WorkoutState.UpdateMode(isUpdate)) }
                    .subscribe()
            }
        }
    }
    private fun workoutAction(action: Workout) {
        when (action) {
            Workout.New -> {
                clearCache()
                exerciseHelper.getAllExercises()
                    .observeUi()
                    .doOnSubscribe { postEventState(WorkoutState.Loading) }
                    .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                    .doAfterSuccess { state -> postEventState(state) }
                    .subscribe()
            }
            is Workout.Load -> {
                clearCache()
                loadWorkoutRecord(action.workoutRecordId)
            }
            Workout.Finish -> finishWorkout()
            Workout.GetCurrent -> {
                exerciseHelper.getAllExercises()
                    .observeUi()
                    .doOnSubscribe { postEventState(WorkoutState.Loading) }
                    .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                    .doAfterSuccess { state -> postEventState(state) }
                    .subscribe()
            }
        }
    }

    private fun postEventState(state: BaseState) {
        when (state) {
            is BaseState.Event -> mEvent.postValue(state as WorkoutEvent)
            is BaseState.State -> mState.value = state as WorkoutState
        }
    }

    private fun loadWorkoutRecord(workoutRecordId: Int) {
        this.workoutRecordId = workoutRecordId
        recordsRepository.getWorkoutWithExerciseAnsSets(workoutRecordId)
            .observeUi()
            .doOnSubscribe { postEventState(WorkoutState.Loading) }
            .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
            .flatMap { dataState ->
                require(dataState is DataState.Success)
                val exercises = dataState.data.exerciseWithSetsList.toCurrent()
                val workout = dataState.data.workout

                dateAction(Date.Set(workout.date))
                if (workout.time != null) {
                    switchAction(SwitchState.Set(true))
                    timeAction(Time.Set(workout.time!!))
                }
                titleAction(Title.Set(workout.title))

                workoutRepository.addExercises(exercises)
            }.doAfterSuccess {
                exerciseHelper.getAllExercises()
                    .observeUi()
                    .doOnSubscribe { postEventState(WorkoutState.Loading) }
                    .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                    .doAfterSuccess { state -> postEventState(state) }
                    .subscribe()
            }
            .subscribe()
    }

    private fun exerciseAction(action: Exercise) {
        when (action) {
            is Exercise.Add -> libraryRepository.getExercise(action.id)
                .observeUi()
                .doOnSubscribe { postEventState(WorkoutState.Loading) }
                .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                .doAfterSuccess { state ->
                    state as DataState.Success
                    val data = state.data
                    navigateHelper.startSetParameterDialog(data.id) { params ->
                        val exerciseDto = CurrentWorkoutDto.Exercise(
                            id = 0,
                            libraryId = action.id,
                            title = data.title
                        )
                        workoutRepository.addExercise(exerciseDto)
                            .flatMap { exerciseId ->
                                val set = CurrentWorkoutDto.Set(
                                    id = 0,
                                    exerciseId = (exerciseId as DataState.Success).data,
                                    setNumber = 1,
                                    weight = params.weight,
                                    reps = params.reps
                                )
                                workoutRepository.addSet(set)
                            }.flatMap {
                                workoutRepository.getExercisesWithSets()
                                    .map { dataState -> dataState.reduce() }
                            }.doAfterSuccess { state -> postEventState(state) }
                            .subscribe()
                    }
                }
                .subscribe()
            is Exercise.Delete -> exerciseHelper.deleteExercise(action.id)
                .observeUi()
                .doOnSubscribe { postEventState(WorkoutState.Loading) }
                .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                .doAfterSuccess { state -> postEventState(state) }
                .subscribe()
        }
    }

    private fun setAction(action: ExSet) {
        when (action) {
            is ExSet.AddExSet -> {
                exerciseHelper.addSetToExercise(action.params)
                    .observeUi()
                    .doOnSubscribe { postEventState(WorkoutState.Loading) }
                    .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                    .doAfterSuccess { state -> postEventState(state) }
                    .subscribe()
            }
            is ExSet.DeleteLastExSet -> {
                exerciseHelper.deleteLastSet(action.exerciseId)
                    .observeUi()
                    .doOnSubscribe { postEventState(WorkoutState.Loading) }
                    .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                    .doAfterSuccess { state -> postEventState(state) }
                    .subscribe()
            }
        }
    }

    private fun navigateAction(action: Navigate) {
        when (action) {
            Navigate.CategoryMenu -> navigateHelper.navigateToCategoryScreen { editStateAction(EditState.Hide) }
            is Navigate.ExerciseDetails -> navigateHelper.navigateToExerciseInfo(action.exLibId) { editStateAction(EditState.Hide) }
            Navigate.Home -> router.navigate(Screen.HOME_SCREEN)
        }
    }

    private fun dialogAction(action: Dialog) {
        when (action) {
            is Dialog.SetParamsDialog -> {
                navigateHelper.startSetParameterDialog(action.exerciseId) { params ->
                    setAction(ExSet.AddExSet(params))
                }
            }
            Dialog.CalendarDialog -> {
                dateManager.getSelectedDate()
                    .observeUi()
                    .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                    .doAfterSuccess { dataState ->
                        val data = (dataState as DataState.Success).data
                        navigateHelper.startCalendarPickerDialog(data) { date -> dateAction(Date.Set(date)) }
                    }.subscribe()
            }
            Dialog.TimeDialog -> {
                timeScheduleManager.getTime()
                    .observeUi()
                    .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                    .doAfterSuccess { time ->
                        require(time is DataState.Success)
                        navigateHelper.startTimePickerDialog(time.data) { newTime -> timeAction(Time.Set(newTime)) }
                    }.subscribe()
            }
            Dialog.TitleDialog -> {
                workoutTitleManager.getTitle()
                    .observeUi()
                    .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                    .doAfterSuccess { title ->
                        navigateHelper.startWorkoutTitleDialog(title) { newTitle ->
                            titleAction(Title.Set(newTitle))
                        }
                    }.subscribe()
            }
            is Dialog.UpdateSetDialog -> {
                navigateHelper.startSetParameterDialog(action.set.exerciseId) { params ->
                    val set = action.set.copy(exerciseId = params.exerciseId, weight = params.weight, reps = params.reps)
                    exerciseHelper.updateSet(set)
                        .observeUi()
                        .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                        .doAfterSuccess { state -> postEventState(state) }
                        .subscribe()
                }
            }
        }
    }

    private fun dateAction(action: Date) {
        when (action) {
            Date.Get -> dateTimeHelper.getDate()
                .observeUi()
                .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                .doAfterSuccess { state -> postEventState(state) }
                .subscribe()
            is Date.Set -> dateTimeHelper.setDate(action.date)
                .observeUi()
                .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                .doAfterSuccess { state -> postEventState(state) }
                .subscribe()
        }
    }

    private fun timeAction(action: Time) {
        when (action) {
            Time.Get -> dateTimeHelper.getTime()
                .observeUi()
                .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                .doAfterSuccess { state -> postEventState(state) }
                .subscribe()
            is Time.Set -> dateTimeHelper.setTime(action.time)
                .observeUi()
                .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                .doAfterSuccess { state -> postEventState(state) }
                .subscribe()
        }
    }

    private fun editStateAction(action: EditState) {
        val event = when (action) {
            EditState.DeleteSelected -> WorkoutEvent.DeleteSelectedExercises
            EditState.DeselectAll -> WorkoutEvent.DeselectAllExercises
            EditState.Hide -> WorkoutEvent.HideEditState
            EditState.SelectAll -> WorkoutEvent.SelectAllExercises
            EditState.Show -> WorkoutEvent.ShowEditState
        }
        mEvent.postValue(event)
    }

    private fun titleAction(action: Title) {
        when (action) {
            Title.Get -> workoutTitleManager.getTitle()
                .observeUi()
                .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                .doAfterSuccess { title -> mState.postValue(WorkoutState.TitleResult(title)) }
                .subscribe()
            is Title.Set -> workoutTitleManager.setTitle(action.title)
                .observeUi()
                .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
                .doAfterSuccess { newTitle -> mState.postValue(WorkoutState.TitleResult(newTitle)) }
                .subscribe()
        }
    }

    private fun switchAction(action: SwitchState) {
        when (action) {
            SwitchState.Get -> {
                mState.postValue(WorkoutState.SwitchState(switchState))
            }
            is SwitchState.Set -> {
                switchState = action.state
                mState.value = WorkoutState.SwitchState(action.state)
            }
        }
    }

    private fun finishWorkout() {
        editStateAction(EditState.Hide)
        workoutHelper.finishWorkout()
            .observeUi()
            .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
            .doAfterSuccess { quad ->
                if (quad.fourth.isEmpty() && !quad.fives) {
                    clearCache()
                    navigateAction(Navigate.Home)
                    return@doAfterSuccess
                } else if (quad.fourth.isEmpty() && quad.fives) {
                    recordsRepository.deleteWorkoutWithExAndSets(workoutRecordId)
                        .observeUi()
                        .doAfterSuccess {
                            clearCache()
                            navigateAction(Navigate.Home)
                        }
                        .subscribe()
                    return@doAfterSuccess
                } else {
                    val time = if (switchState) { quad.second } else { null }
                    save(quad.third, quad.first, time, quad.fourth, workoutRecordId)
                    workoutRecordId = 0
                }
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
            .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
            .flatMap { state ->
                postEventState(state)
                dateManager.selectDate(date)
            }.flatMap {
                dateManager.selectMonth(date)
            }.subscribe()
    }

    private fun close(workoutId: Int) {
        recordsRepository.getWorkoutWithExerciseAnsSets(workoutId)
            .observeUi()
            .doOnError { postEventState(WorkoutEvent.ExceptionResult(it as Exception)) }
            .doAfterSuccess { dataState ->
                if (dataState is DataState.Success &&
                    dataState.data.workout.time != null
                ) {
                    val workoutDto = dataState.reduceWorkoutDto()
                    notificationHelper.startWorkoutReminderEvent(workoutDto)
                }
                clearCache()
                navigateAction(Navigate.Home)
            }.subscribe()
    }

    private fun clearCache() {
        workoutRepository.clearCache()
        timeScheduleManager.clearCache()
        workoutTitleManager.clearCache()
    }

    private fun toast(text: String) {
        navigateHelper.showToast(text)
    }
}
