package com.lefarmico.workout.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.screen.Screen
import com.lefarmico.workout.extensions.toRecordsDto
import com.lefarmico.workout.intent.WorkoutScreenIntent
import javax.inject.Inject

class WorkoutScreenViewModel @Inject constructor() : BaseViewModel<WorkoutScreenIntent>() {

    @Inject lateinit var recordsRepository: WorkoutRecordsRepository
    @Inject lateinit var libraryRepository: LibraryRepository
    @Inject lateinit var repo: CurrentWorkoutRepository
    @Inject lateinit var router: Router

    // TODO : убрать локальные переменные
    private var exerciseIds = 1
    private var setId = 1

    val exerciseLiveData = MutableLiveData<DataState<List<CurrentWorkoutDto.ExerciseWithSets>>>()

    private fun addExercise(model: WorkoutScreenIntent.AddExercise) {
        libraryRepository.getExercise(model.id)
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val exercise = CurrentWorkoutDto.Exercise.Builder()
                            .setTitle(dataState.data.title)
                            .setLibraryId(model.id)
                            .build()
                        repo.addExercise(exercise)
                            .doOnSuccess {
                                getAll()
                            }
                            .subscribe()
                    }
                    else -> {}
                }
            }
    }

    private fun deleteExercise(model: WorkoutScreenIntent.DeleteExercise) {
        repo.getExerciseWithSets(model.id)
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        repo.deleteExercise(dataState.data.exercise.id)
                            .doOnSuccess {
                                getAll()
                            }
                            .subscribe()
                        getAll()
                    }
                    else -> {}
                }
            }
    }

    private fun getAll() {
        repo.getExercisesWithSets()
            .subscribe { dataState ->
                exerciseLiveData.postValue(dataState)
            }
    }

    private fun saveWorkout() {
        repo.getExercisesWithSets()
            .doAfterSuccess {
                repo.clearCash()
            }
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val workoutDto = dataState.data.toRecordsDto()
                        recordsRepository.addWorkoutWithExAndSets(workoutDto)
                            .subscribe()
                    }
                    else -> {
                        exerciseLiveData.postValue(dataState)
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
            .doAfterSuccess {
                repo.addSet(
                    CurrentWorkoutDto.Set(
                        id = setId++,
                        exerciseId = exerciseId,
                        setNumber = setNumber,
                        weight = weight,
                        reps = reps
                    )
                ).doAfterSuccess {
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
            .doAfterSuccess {
                getAll()
            }.subscribe()
    }

    private fun goToCategoryScreen() {
        router.navigate(
            screen = Screen.CATEGORY_LIST_SCREEN,
            data = LibraryParams.CategoryList(true)
        )
    }

    private fun finishWorkout() {
        saveWorkout()
        router.navigate(
            screen = Screen.HOME_SCREEN
        )
    }

    private fun goToExerciseInfo(libraryId: Int) {
        router.navigate(
            screen = Screen.EXERCISE_DETAILS_SCREEN_FROM_WORKOUT,
            data = LibraryParams.Exercise(libraryId)
        )
    }
    override fun onTriggerEvent(eventType: WorkoutScreenIntent) {
        when (eventType) {
            is WorkoutScreenIntent.AddExercise -> addExercise(eventType)

            is WorkoutScreenIntent.DeleteExercise -> deleteExercise(eventType)

            WorkoutScreenIntent.GetAll -> getAll()

            is WorkoutScreenIntent.AddSetToExercise -> addSetToExercise(
                eventType.exerciseId,
                eventType.reps,
                eventType.weight
            )
            is WorkoutScreenIntent.DeleteLastSet -> deleteLastSet(eventType.exerciseId)

            WorkoutScreenIntent.GoToCategoryScreen -> goToCategoryScreen()

            WorkoutScreenIntent.FinishWorkout -> finishWorkout()

            is WorkoutScreenIntent.GoToExerciseInfo -> goToExerciseInfo(eventType.libraryId)
        }
    }
}
