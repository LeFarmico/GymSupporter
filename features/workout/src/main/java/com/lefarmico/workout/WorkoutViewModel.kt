package com.lefarmico.workout

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.*
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.dialog.Dialog
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.SetParameterParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.screen.Screen
import com.lefarmico.workout.WorkoutAction.EditState.Action
import com.lefarmico.workout.WorkoutAction.EditState.Action.*
import com.lefarmico.workout.WorkoutAction.EditState.Action.DeselectAll
import com.lefarmico.workout.WorkoutIntent.*
import com.lefarmico.workout.extensions.toRecordsDto
import java.time.LocalDate
import javax.inject.Inject

class WorkoutViewModel @Inject constructor() : BaseViewModel<
    WorkoutIntent, WorkoutAction, WorkoutState, WorkoutEvent
    >() {

    @Inject lateinit var recordsRepository: WorkoutRecordsRepository
    @Inject lateinit var libraryRepository: LibraryRepository
    @Inject lateinit var repo: CurrentWorkoutRepository
    @Inject lateinit var router: Router
    @Inject lateinit var dateTimeRepository: DateTimeManager
    @Inject lateinit var formatterManager: FormatterManager

    // TODO : убрать локальные переменные
    private var setId = 1
    private var title = "Your workout"

    private fun addExercise(id: Int) {
        libraryRepository.getExercise(id)
            .observeUi()
            .flatMap {
                val data = (it as DataState.Success).data
                val ex = CurrentWorkoutDto.Exercise(0, id, data.title)
                repo.addExercise(ex).observeUi()
            }
            .doAfterSuccess { getAll() }
            .doOnSuccess { exId -> mEvent.postValue(exId.reduce()) }
            .subscribe()
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
            .doAfterSuccess { dataState -> mState.value = dataState.reduce() }
            .subscribe()
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
        editStateAction(Hide)
        router.navigate(
            screen = Screen.CATEGORY_LIST_SCREEN,
            data = LibraryParams.CategoryList(true)
        )
    }

    private fun finishWorkout() {
        editStateAction(Hide)
        dateTimeRepository.getSelectedDate()
            .observeUi()
            .doAfterSuccess {
                val data = (it as DataState.Success).data
                saveWorkout(title, data)
            }.subscribe()
    }

    private fun goToExerciseInfo(libraryId: Int) {
        editStateAction(Hide)
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

    private fun editStateAction(action: Action) {
        val event = when (action) {
            DeselectAll -> WorkoutEvent.DeselectAllExercises
            Hide -> WorkoutEvent.HideEditState
            SelectAll -> WorkoutEvent.SelectAllExercises
            Show -> WorkoutEvent.ShowEditState
            DeleteSelected -> WorkoutEvent.DeleteSelectedExercises
        }
        mEvent.postValue(event)
    }

    // TODO убрать вложенность
    private fun setWorkoutDate(localDate: LocalDate) {
        formatterManager.getSelectedFormatter()
            .observeUi()
            .doAfterSuccess { dto ->
                dateTimeRepository.selectDate(localDate)
                    .observeUi()
                    .doAfterSuccess { date -> mState.value = date.reduce(dto.formatter) }
                    .subscribe()
            }.subscribe()
    }

    // TODO убрать вложенность
    private fun getWorkoutDate() {
        formatterManager.getSelectedFormatter()
            .observeUi()
            .doAfterSuccess { dto ->
                dateTimeRepository.getSelectedDate()
                    .observeUi()
                    .doAfterSuccess { date -> mState.value = date.reduce(dto.formatter) }
                    .subscribe()
            }.subscribe()
    }

    private fun setWorkoutTitle(title: String) {
        mState.value = WorkoutState.TitleResult(title)
        this.title = title
    }

    private fun getWorkoutTitle() {
        mState.value = WorkoutState.TitleResult(title)
    }

    override fun triggerAction(action: WorkoutAction) {
        when (action) {
            is WorkoutAction.EditState -> editStateAction(action.action)
            is WorkoutAction.AddSetToExercise -> addSetToExercise(action.params)
            is WorkoutAction.AddExercise -> addExercise(action.id)
            is WorkoutAction.DeleteExercise -> deleteExercise(action.id)
            is WorkoutAction.DeleteLastSet -> deleteLastSet(action.exerciseId)
            WorkoutAction.FinishWorkout -> finishWorkout()
            WorkoutAction.GetExercises -> getAll()
            WorkoutAction.GetSelectedDate -> getWorkoutDate()
            WorkoutAction.GoToCategoryScreen -> goToCategoryScreen()
            is WorkoutAction.GoToExerciseInfo -> goToExerciseInfo(action.libraryId)
            is WorkoutAction.ShowDialog -> showDialog(action.dialog)
            is WorkoutAction.ShowToast -> showToast(action.text)
            WorkoutAction.StartCalendarPickerDialog -> showCalendarPickerDialog()
            is WorkoutAction.StartSetParameterDialog -> showSetParameterDialog(action.exerciseId)
            WorkoutAction.StartWorkoutTitleDialog -> showWorkoutTitleDialog()
            WorkoutAction.GetTitle -> getWorkoutTitle()
        }
    }

    override fun intentToAction(intent: WorkoutIntent): WorkoutAction {
        return when (intent) {
            is AddExercise -> WorkoutAction.AddExercise(intent.id)
            is AddSetToExercise -> WorkoutAction.AddSetToExercise(intent.params)
            is DeleteExercise -> WorkoutAction.DeleteExercise(intent.id)
            is DeleteLastSet -> WorkoutAction.DeleteLastSet(intent.exerciseId)
            FinishWorkout -> WorkoutAction.FinishWorkout
            GetExercises -> WorkoutAction.GetExercises
            GetSelectedDate -> WorkoutAction.GetSelectedDate
            GoToCategoryScreen -> WorkoutAction.GoToCategoryScreen
            is GoToExerciseInfo -> WorkoutAction.GoToExerciseInfo(intent.libraryId)
            is ShowDialog -> WorkoutAction.ShowDialog(intent.dialog)
            is ShowToast -> WorkoutAction.ShowToast(intent.text)
            StartCalendarPickerDialog -> WorkoutAction.StartCalendarPickerDialog
            is StartSetParameterDialog -> WorkoutAction.StartSetParameterDialog(intent.exerciseId)
            StartWorkoutTitleDialog -> WorkoutAction.StartWorkoutTitleDialog

            HideEditState -> WorkoutAction.EditState(Hide)
            ShowEditState -> WorkoutAction.EditState(Show)
            SelectAllWorkouts -> WorkoutAction.EditState(SelectAll)
            DeleteSelectedWorkouts -> WorkoutAction.EditState(DeselectAll)
            WorkoutIntent.DeselectAll -> WorkoutAction.EditState(DeleteSelected)
            GetTitle -> WorkoutAction.GetTitle
        }
    }
}
