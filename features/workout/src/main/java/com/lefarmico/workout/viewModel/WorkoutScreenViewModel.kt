package com.lefarmico.workout.viewModel

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.dialog.setParameter.SetParametersDialog
import com.lefarmico.core.dialog.setParameter.SetSettingDialogCallback
import com.lefarmico.core.entity.CurrentWorkoutViewData
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toViewDataExWithSets
import com.lefarmico.core.toolbar.RemoveActionBarEvents
import com.lefarmico.core.utils.SingleLiveEvent
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
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
    val actionBarLiveData = SingleLiveEvent<RemoveActionBarEvents>()
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
                            .subscribe { dataStateId ->
                                setParametersDialogLiveData.postValue(dataStateId)
                            }
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
            .doOnSubscribe { exerciseLiveData.postValue(DataState.Loading) }
            .doAfterSuccess { dataState ->
                when (dataState) {
                    DataState.Empty -> exerciseLiveData.postValue(DataState.Empty)
                    DataState.Loading -> exerciseLiveData.postValue(DataState.Loading)
                    is DataState.Error -> exerciseLiveData.postValue(dataState)
                    is DataState.Success -> {
                        val success = DataState.Success(dataState.data.toViewDataExWithSets())
                        exerciseLiveData.postValue(success)
                    }
                }
            }.subscribe()
    }

    private fun saveWorkout() {
        repo.getExercisesWithSets()
            .observeUi()
            .doAfterSuccess {
                repo.clearCash()
                notificationLiveData.postValue("Workout successfully saved.")
            }
            .subscribe { dataState ->
                when (dataState) {
                    DataState.Empty -> exerciseLiveData.postValue(DataState.Empty)
                    DataState.Loading -> exerciseLiveData.postValue(DataState.Loading)
                    is DataState.Error -> exerciseLiveData.postValue(dataState)
                    is DataState.Success -> {
                        val workoutDto = dataState.data.toRecordsDto()
                        recordsRepository
                            .addWorkoutWithExAndSets(workoutDto)
                            .observeUi()
                            .subscribe()
                    }
                }
            }
    }

    // TODO : убрать локальные переменные
    private fun addSetToExercise(
        exerciseId: Int,
        reps: Int,
        weight: Float
    ) {
        var setNumber = 0
        repo.getSets(exerciseId)
            .observeUi()
            .doAfterSuccess {
                repo.addSet(
                    CurrentWorkoutDto.Set(
                        id = setId++,
                        exerciseId = exerciseId,
                        setNumber = setNumber,
                        weight = weight,
                        reps = reps
                    )
                )
                    .observeUi()
                    .doAfterSuccess {
                        getAll()
                    }.subscribe()
            }
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> setNumber = dataState.data.size + 1
                    else -> {}
                }
            }
    }

    private fun deleteLastSet(exerciseId: Int) {
        repo.deleteLastSet(exerciseId)
            .observeUi()
            .doAfterSuccess { getAll() }
            .subscribe()
    }

    private fun goToCategoryScreen() {
        actionBarLiveData.postValue(RemoveActionBarEvents.Close)
        router.navigate(
            screen = Screen.CATEGORY_LIST_SCREEN,
            data = LibraryParams.CategoryList(true)
        )
    }

    private fun finishWorkout() {
        actionBarLiveData.postValue(RemoveActionBarEvents.Close)
        saveWorkout()
        router.navigate(Screen.HOME_SCREEN)
    }

    private fun goToExerciseInfo(libraryId: Int) {
        actionBarLiveData.postValue(RemoveActionBarEvents.Close)
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

    private fun actionBarEvent(actionBarEvent: RemoveActionBarEvents) {
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
