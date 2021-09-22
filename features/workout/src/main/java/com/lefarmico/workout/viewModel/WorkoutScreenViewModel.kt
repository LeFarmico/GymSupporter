package com.lefarmico.workout.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.utils.Utilities
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.screen.Screen
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

    val exerciseLiveData = MutableLiveData<DataState<List<WorkoutRecordsDto.Exercise>>>()

    fun addExercise(model: WorkoutScreenIntent.AddExercise) {
        libraryRepository.getExercise(model.id)
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val exerciseDto = WorkoutRecordsDto.Exercise(
                            id = exerciseIds++,
                            exerciseName = dataState.data.title,
                            noteSetList = mutableListOf()
                        )
                        repo.addExercise(exerciseDto).subscribe()
                        getAll()
                    }
                    else -> {}
                }
            }
    }

    fun deleteExercise(model: WorkoutScreenIntent.DeleteExercise) {
        repo.getExercise(model.id)
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        repo.deleteExercise(dataState.data).subscribe()
                        getAll()
                    }
                    else -> {}
                }
            }
    }

    fun getAll() {
        repo.getExercises()
            .subscribe { dataState ->
                exerciseLiveData.postValue(dataState)
            }
    }

    fun saveWorkout() {
        repo.getExercises()
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val workoutDto = WorkoutRecordsDto.Workout(
                            date = Utilities.getCurrentDateInFormat(),
                            exerciseList = dataState.data
                        )
                        recordsRepository.addWorkout(workoutDto).subscribe()
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
        repo.getSets(exerciseId).subscribe { dataState ->
            when (dataState) {
                is DataState.Success -> setNumber = dataState.data.size
                else -> {}
            }
        }
        repo.addSet(
            WorkoutRecordsDto.Set(
                id = setId++,
                exerciseId = exerciseId,
                setNumber = setNumber,
                weight = weight,
                reps = reps,
                measureType = WorkoutRecordsDto.MeasureType.KILO
            )
        ).subscribe()
        getAll()
    }

    fun deleteSet(exerciseId: Int) {
        repo.deleteLastSet(exerciseId).subscribe()
        repo.getExercise(exerciseId).subscribe { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    val setList = dataState.data.noteSetList
                    if (setList.isEmpty()) {
                        onTriggerEvent(
                            WorkoutScreenIntent.DeleteExercise(dataState.data.id)
                        )
                    }
                }
                else -> {}
            }
        }
        getAll()
    }

    fun getExercise(exerciseId: Int) {
        repo.getExercise(exerciseId).subscribe { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    exerciseLiveData.postValue(
                        DataState.Success(listOf(dataState.data))
                    )
                }
                else -> {}
            }
        }
    }

    private fun goToCategoryScreen() {
        router.navigate(
            screen = Screen.CATEGORY_SCREEN_FROM_LIBRARY,
            data = LibraryParams.CategoryList(true)
        )
    }

    private fun finishWorkout() {
        router.navigate(
            screen = Screen.HOME_SCREEN
        )
    }
    override fun onTriggerEvent(eventType: WorkoutScreenIntent) {
        when (eventType) {
            is WorkoutScreenIntent.AddExercise -> addExercise(eventType)

            is WorkoutScreenIntent.DeleteExercise -> deleteExercise(eventType)

            WorkoutScreenIntent.GetAll -> getAll()

            WorkoutScreenIntent.SaveAll -> saveWorkout()

            is WorkoutScreenIntent.AddSetToExercise -> addSetToExercise(
                eventType.exerciseId,
                eventType.reps,
                eventType.weight
            )
            is WorkoutScreenIntent.DeleteSet -> deleteSet(eventType.exerciseId)

            is WorkoutScreenIntent.GetExercise -> TODO()
            is WorkoutScreenIntent.GoToCategoryScreen -> goToCategoryScreen()
            is WorkoutScreenIntent.FinishWorkout -> finishWorkout()
        }
    }
}
