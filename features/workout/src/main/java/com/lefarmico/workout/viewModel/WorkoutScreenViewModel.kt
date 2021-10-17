package com.lefarmico.workout.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.utils.Utilities
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.entity.WorkoutRecordsDto
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
                        val exercise = CurrentWorkoutDto.ExerciseWithSets(
                            exercise = CurrentWorkoutDto.Exercise(
                                id = exerciseIds++,
                                exerciseName = dataState.data.title
                            ),
                            setList = mutableListOf()
                        )
                        repo.addExercise(exercise.exercise).subscribe()
                        getAll()
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
                        repo.deleteExerciseWithSets(dataState.data).subscribe()
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
            .doOnTerminate {
                repo.clearCash()
            }
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val workoutDto = WorkoutRecordsDto.Workout(
                            date = Utilities.getCurrentDateInFormat()
                        )
                        // TODO: workoutId
                        val exerciseList = dataState.data.map {
                            WorkoutRecordsDto.Exercise(
                                id = it.exercise.id,
                                workoutId = workoutDto.id,
                                exerciseName = it.exercise.exerciseName
                            )
                        }
                        val setList = dataState.data.flatMap {
                            it.setList.map { set ->
                                set.toRecordsDto()
                            }
                        }

                        recordsRepository.addWorkout(workoutDto).subscribe()
                        recordsRepository.addExerciseList(exerciseList)
                        recordsRepository.addSetList(setList)
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
            CurrentWorkoutDto.Set(
                id = setId++,
                exerciseId = exerciseId,
                setNumber = setNumber,
                weight = weight,
                reps = reps,
                measureType = CurrentWorkoutDto.MeasureType.KILO
            )
        ).subscribe()
        getAll()
    }

    private fun deleteSet(set: CurrentWorkoutDto.Set) {
        repo.deleteSet(set).subscribe()
        repo.getSets(set.exerciseId).subscribe { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    val setList = dataState.data
                    if (setList.isEmpty()) {
                        onTriggerEvent(
                            WorkoutScreenIntent.DeleteExercise(set.exerciseId)
                        )
                    }
                }
                else -> {}
            }
        }
        getAll()
    }

    fun getExercise(exerciseId: Int) {
        repo.getExerciseWithSets(exerciseId).subscribe { dataState ->
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
            is WorkoutScreenIntent.DeleteSet -> deleteSet(eventType.set)

            is WorkoutScreenIntent.GetExercise -> TODO()

            WorkoutScreenIntent.GoToCategoryScreen -> goToCategoryScreen()

            WorkoutScreenIntent.FinishWorkout -> finishWorkout()
        }
    }
}
