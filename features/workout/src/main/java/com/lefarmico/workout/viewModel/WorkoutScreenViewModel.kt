package com.lefarmico.workout.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.CurrentWorkoutViewData
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toViewDataExWithSets
import com.lefarmico.core.toolbar.EditActionBarEvents
import com.lefarmico.core.utils.SingleLiveEvent
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.*
import com.lefarmico.domain.utils.DataState
import com.lefarmico.domain.utils.map
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.dialog.Dialog
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.SetParameterParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.screen.Screen
import com.lefarmico.workout.extensions.toRecordsDto
import com.lefarmico.workout.intent.WorkoutScreenIntent
import com.lefarmico.workout.intent.WorkoutScreenIntent.*
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class WorkoutScreenViewModel @Inject constructor() : BaseViewModel<WorkoutScreenIntent>() {

    @Inject lateinit var recordsRepository: WorkoutRecordsRepository
    @Inject lateinit var libraryRepository: LibraryRepository
    @Inject lateinit var repo: CurrentWorkoutRepository
    @Inject lateinit var router: Router
    @Inject lateinit var dateTimeRepository: DateTimeManager
    @Inject lateinit var formatterManager: FormatterManager

    // TODO : убрать локальные переменные
    private var setId = 1
    private var title = "Your workout"

    val exerciseLiveData = MutableLiveData<DataState<List<CurrentWorkoutViewData.ExerciseWithSets>>>()
    val formattedDateLiveData = MutableLiveData<String>()
    val titleLiveData = MutableLiveData<String>()

    val setParametersDialogLiveData = SingleLiveEvent<DataState<Long>>()
    val actionBarLiveData = SingleLiveEvent<EditActionBarEvents>()
    val notifyLiveData = SingleLiveEvent<String>()

    init {

        titleLiveData.value = title
    }

    private fun addExercise(model: AddExercise) {
        libraryRepository.getExercise(model.id)
            .observeUi()
            .flatMap {
                val data = (it as DataState.Success).data
                val ex = CurrentWorkoutDto.Exercise(0, model.id, data.title)
                repo.addExercise(ex).observeUi()
            }
            .doAfterSuccess { getAll() }
            .subscribe { id -> setParametersDialogLiveData.postValue(id) }
    }

    private fun deleteExercise(exerciseId: Int) {
        repo.deleteExercise(exerciseId)
            .observeUi()
            .doAfterSuccess { getAll() }
            .subscribe()
    }

    private fun getAll() {
        repo.getExercisesWithSets()
            .observeUi()
            .doAfterSuccess { dataState ->
                val viewDataState = dataState.map { it.toViewDataExWithSets() }
                exerciseLiveData.value = viewDataState
            }.subscribe()
    }

    private fun saveWorkout(title: String, date: LocalDate) {
        repo.getExercisesWithSets()
            .observeUi()
            .flatMap {
                val data = (it as DataState.Success).data
                val workoutDto = WorkoutRecordsDto.Workout(date = date, title = title)
                val workAndExDto = WorkoutRecordsDto.WorkoutWithExercisesAndSets(
                    workoutDto,
                    data.toRecordsDto()
                )
                recordsRepository.addWorkoutWithExAndSets(workAndExDto).observeUi()
            }.doAfterSuccess {
                if (it is DataState.Error) {
                    throw (it.exception)
                }
                repo.clearCash()
                router.navigate(Screen.HOME_SCREEN)
            }.subscribe()
    }

    // TODO : убрать локальные переменные
    private fun addSetToExercise(params: SetParameterParams) {
        repo.getSets(params.exerciseId)
            .observeUi()
            .flatMap {
                var setNumber = 1
                if (it is DataState.Success) {
                    setNumber = it.data.size + 1
                }
                val set = CurrentWorkoutDto.Set(setId++, params.exerciseId, setNumber, params.weight, params.reps)
                repo.addSet(set).observeUi()
            }
            .doAfterSuccess { getAll() }
            .subscribe()
    }

    private fun deleteLastSet(exerciseId: Int) {
        repo.deleteLastSet(exerciseId)
            .observeUi()
            .doAfterSuccess { getAll() }
            .subscribe()
    }

    private fun goToCategoryScreen() {
        actionBarLiveData.postValue(EditActionBarEvents.Close)
        router.navigate(
            screen = Screen.CATEGORY_LIST_SCREEN,
            data = LibraryParams.CategoryList(true)
        )
    }

    private fun finishWorkout() {
        actionBarLiveData.postValue(EditActionBarEvents.Close)
        dateTimeRepository.getSelectedDate()
            .observeUi()
            .doAfterSuccess {
                val data = (it as DataState.Success).data
                saveWorkout(title, data)
            }.subscribe()
    }

    private fun goToExerciseInfo(libraryId: Int) {
        actionBarLiveData.postValue(EditActionBarEvents.Close)
        router.navigate(
            screen = Screen.EXERCISE_DETAILS_SCREEN_FROM_WORKOUT,
            data = LibraryParams.Exercise(libraryId)
        )
    }

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }

    private fun showDialog(dialog: Dialog) {
        router.showDialog(dialog)
    }

    private fun showCalendarPickerDialog() {
        dateTimeRepository.getSelectedDate()
            .observeUi()
            .doAfterSuccess {
                val data = (it as DataState.Success).data
                val dialog = Dialog.CalendarPickerDialog(data) { clickedDate ->
                    setWorkoutDate(clickedDate)
                }
                router.showDialog(dialog)
            }.subscribe()
    }

    private fun showWorkoutTitleDialog() {
        val dialog = Dialog.FieldEditorDialog(title) {
            setWorkoutTitle(it)
        }
        router.showDialog(dialog)
    }

    private fun showSetParameterDialog(exerciseId: Int) {
        val dialog = Dialog.SetParameterPickerDialog(exerciseId) {
            addSetToExercise(it)
        }
        router.showDialog(dialog)
    }

    private fun actionBarEvent(actionBarEvent: EditActionBarEvents) {
        actionBarLiveData.postValue(actionBarEvent)
    }

    // TODO убрать вложенность
    private fun setWorkoutDate(localDate: LocalDate) {
        formatterManager.getSelectedFormatter()
            .observeUi()
            .doAfterSuccess { dto ->
                dateTimeRepository.selectDate(localDate)
                    .observeUi()
                    .doAfterSuccess {
                        val data = (it as DataState.Success).data
                        formattedDateLiveData.value = data.format(dto.formatter)
                    }.subscribe()
            }.subscribe()
    }

    // TODO убрать вложенность
    private fun getWorkoutDate() {
        formatterManager.getSelectedFormatter()
            .observeUi()
            .doAfterSuccess { dto ->
                dateTimeRepository.getSelectedDate()
                    .observeUi()
                    .doAfterSuccess {
                        val data = (it as DataState.Success).data
                        formattedDateLiveData.value = data.format(dto.formatter)
                    }.subscribe()
            }.subscribe()
    }

    private fun setWorkoutTitle(title: String) {
        titleLiveData.value = title
        this.title = title
    }

    override fun onTriggerEvent(eventType: WorkoutScreenIntent) {
        when (eventType) {
            is AddExercise -> addExercise(eventType)
            is DeleteExercise -> deleteExercise(eventType.id)
            is DeleteLastSet -> deleteLastSet(eventType.exerciseId)
            is GoToExerciseInfo -> goToExerciseInfo(eventType.libraryId)
            is ShowToast -> showToast(eventType.text)
            is ActionBarEvent -> actionBarEvent(eventType.event)
            is ShowDialog -> showDialog(eventType.dialog)
            is AddSetToExercise -> addSetToExercise(eventType.params)
            FinishWorkout -> finishWorkout()
            GetExercises -> getAll()
            GoToCategoryScreen -> goToCategoryScreen()
            is StartSetParameterDialog -> showSetParameterDialog(eventType.exerciseId)
            StartCalendarPickerDialog -> showCalendarPickerDialog()
            StartWorkoutTitleDialog -> showWorkoutTitleDialog()
            GetSelectedDate -> getWorkoutDate()
        }
    }
}
