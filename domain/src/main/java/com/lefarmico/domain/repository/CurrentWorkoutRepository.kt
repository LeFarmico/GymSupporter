package com.lefarmico.domain.repository

import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single

interface CurrentWorkoutRepository : BaseRepository {

    fun getExercisesWithSets(): Single<DataState<List<CurrentWorkoutDto.ExerciseWithSets>>>

    fun getSets(exerciseId: Int): Single<DataState<List<CurrentWorkoutDto.Set>>>

    fun addExercise(exercise: CurrentWorkoutDto.Exercise): Single<DataState<Long>>

    fun addSet(set: CurrentWorkoutDto.Set): Single<DataState<Long>>

    fun deleteExercise(exerciseId: Int): Single<DataState<Long>>

    fun deleteSet(set: CurrentWorkoutDto.Set): Single<DataState<Long>>

    fun deleteLastSet(exerciseId: Int): Single<DataState<Long>>

    fun getExerciseWithSets(exerciseId: Int): Single<DataState<CurrentWorkoutDto.ExerciseWithSets>>

    fun clearCash()
}
