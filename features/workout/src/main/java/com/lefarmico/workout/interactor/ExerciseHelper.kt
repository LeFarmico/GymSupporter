package com.lefarmico.workout.interactor

import com.lefarmico.core.base.BaseState
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.params.SetParameterParams
import com.lefarmico.workout.WorkoutEvent
import com.lefarmico.workout.WorkoutState
import com.lefarmico.workout.reduce
import io.reactivex.rxjava3.core.Single

class ExerciseHelper(
    private val workoutRepository: CurrentWorkoutRepository,
    private val libraryRepository: LibraryRepository
) {

    private var setId = 1

    fun addExercise(id: Int, idCallback: (BaseState) -> Unit): Single<BaseState> {
        return libraryRepository.getExercise(id)
            .flatMap {
                val data = (it as DataState.Success).data
                val ex = CurrentWorkoutDto.Exercise(0, id, data.title)
                workoutRepository.addExercise(ex)
                    .doAfterSuccess { exId -> idCallback(exId.reduce()) }
            }.flatMap { getAllExercises() }
    }

    fun deleteExercise(id: Int): Single<BaseState> {
        return workoutRepository.deleteExercise(id)
            .flatMap { getAllExercises() }
    }

    fun addSetToExercise(params: SetParameterParams): Single<BaseState> {
        return workoutRepository.getSets(params.exerciseId)
            .flatMap {
                var setNumber = 1
                if (it is DataState.Success) {
                    setNumber = it.data.size + 1
                }
                val set = CurrentWorkoutDto.Set(setId++, params.exerciseId, setNumber, params.weight, params.reps)
                workoutRepository.addSet(set)
            }.flatMap { getAllExercises() }
    }

    fun deleteLastSet(exerciseId: Int): Single<BaseState> {
        return workoutRepository.deleteLastSet(exerciseId)
            .flatMap { getAllExercises() }
    }

    fun getAllExercises(): Single<BaseState> {
        return workoutRepository.getExercisesWithSets()
            .map { dataState -> dataState.reduce() }
    }
}
