package com.lefarmico.workout.viewModel

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.dialog.setParameter.SetParametersDialog
import com.lefarmico.core.dialog.setParameter.SetSettingDialogCallback
import com.lefarmico.core.entity.CurrentWorkoutViewData
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toViewDataExWithSets
import com.lefarmico.core.toolbar.EditActionBarEvents
import com.lefarmico.core.utils.SingleLiveEvent
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.domain.utils.map
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.screen.Screen
import com.lefarmico.workout.extensions.toRecordsDto
import com.lefarmico.workout.intent.WorkoutScreenIntent
import com.lefarmico.workout.intent.WorkoutScreenIntent.*
import javax.inject.Inject

class WorkoutScreenViewModel @Inject constructor() : BaseViewModel<WorkoutScreenIntent>() {

    @Inject lateinit var recordsRepository: WorkoutRecordsRepository
    @Inject lateinit var libraryRepository: LibraryRepository
    @Inject lateinit var repo: CurrentWorkoutRepository
    @Inject lateinit var router: Router

    // TODO : убрать локальные переменные
    private var setId = 1

    val exerciseLiveData = MutableLiveData<DataState<List<CurrentWorkoutViewData.ExerciseWithSets>>>()
    val setParametersDialogLiveData = SingleLiveEvent<DataState<Long>>()
    val actionBarLiveData = SingleLiveEvent<EditActionBarEvents>()
    val notificationLiveData = SingleLiveEvent<String>()

    // TODO переписать
    private fun addExercise(model: AddExercise) {
        libraryRepository.getExercise(model.id)
            .observeUi()
            .doAfterSuccess { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val exercise = CurrentWorkoutDto.Exercise(
                            libraryId = model.id,
                            title = dataState.data.title
                        )
                        repo.addExercise(exercise)
                            .observeUi()
                            .doAfterSuccess { getAll() }
                            .subscribe { id -> setParametersDialogLiveData.postValue(id) }
                    }
                    else -> {}
                }
            }.subscribe()
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

    private fun saveWorkout() {
        repo.getExercisesWithSets()
            .observeUi()
            .doAfterSuccess {
                repo.clearCash()
            }
            .doOnSuccess { dataState ->
                when (dataState) {
                    DataState.Empty -> exerciseLiveData.value = DataState.Empty
                    DataState.Loading -> exerciseLiveData.value = DataState.Loading
                    is DataState.Error -> exerciseLiveData.value = dataState
                    is DataState.Success -> {
                        val workoutDto = dataState.data.toRecordsDto()
                        recordsRepository
                            .addWorkoutWithExAndSets(workoutDto)
                            .observeUi()
                            .doAfterSuccess { router.navigate(Screen.HOME_SCREEN) }
                            .subscribe()
                    }
                }
            }.subscribe()
    }

    // TODO : убрать локальные переменные
    private fun addSetToExercise(
        exerciseId: Int,
        reps: Int,
        weight: Float
    ) {
        repo.getSets(exerciseId)
            .observeUi()
            .doAfterSuccess { dataState ->
                var setNumber = 1
                if (dataState is DataState.Success) {
                    setNumber = dataState.data.size + 1
                }
                repo.addSet(
                    CurrentWorkoutDto.Set(setId++, exerciseId, setNumber, weight, reps)
                )
                    .observeUi()
                    .doAfterSuccess { getAll() }
                    .subscribe()
            }.subscribe()
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
        saveWorkout()
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

    private fun initSetParameterDialog(
        fragmentManager: FragmentManager,
        exerciseId: Int,
        callback: SetSettingDialogCallback,
        tag: String
    ) {
        SetParametersDialog(exerciseId, callback).show(fragmentManager, tag)
    }

    private fun actionBarEvent(actionBarEvent: EditActionBarEvents) {
        actionBarLiveData.postValue(actionBarEvent)
    }

    override fun onTriggerEvent(eventType: WorkoutScreenIntent) {
        when (eventType) {
            is AddExercise -> addExercise(eventType)

            is DeleteExercise -> deleteExercise(eventType.id)

            GetAll -> getAll()

            GoToCategoryScreen -> goToCategoryScreen()

            FinishWorkout -> finishWorkout()

            is DeleteLastSet -> deleteLastSet(eventType.exerciseId)

            is GoToExerciseInfo -> goToExerciseInfo(eventType.libraryId)

            is ShowToast -> showToast(eventType.text)

            is ActionBarEvent -> actionBarEvent(eventType.event)

            is AddSetToExercise -> addSetToExercise(
                eventType.exerciseId,
                eventType.reps,
                eventType.weight
            )

            is ShowSetParametersDialog -> initSetParameterDialog(
                eventType.fragmentManager,
                eventType.exerciseId,
                eventType.callback,
                eventType.tag
            )
        }
    }
}
