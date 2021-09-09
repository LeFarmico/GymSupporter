package com.lefarmico.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.data.repository.CurrentWorkoutRepositoryImpl
import com.lefarmico.data.repository.LibraryRepositoryImpl
import com.lefarmico.data.repository.WorkoutRecordsRepositoryImpl
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.presentation.intents.WorkoutScreenIntent
import com.lefarmico.presentation.utils.Utilities
import com.lefarmico.presentation.views.base.BaseViewModel
import javax.inject.Inject

class WorkoutScreenViewModel : BaseViewModel<WorkoutScreenIntent>() {

    @Inject lateinit var recordsRepository: WorkoutRecordsRepositoryImpl
    @Inject lateinit var libraryRepository: LibraryRepositoryImpl
    @Inject lateinit var repo: CurrentWorkoutRepositoryImpl

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
    fun addSetToExercise(
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
        }
    }
}
