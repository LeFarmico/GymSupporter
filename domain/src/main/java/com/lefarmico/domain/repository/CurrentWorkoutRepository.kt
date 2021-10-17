package com.lefarmico.domain.repository

import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CurrentWorkoutRepository : BaseRepository {

    fun addExercise(exercise: CurrentWorkoutDto.Exercise): Single<DataState<Long>>

    fun getExercisesWithSets(): Observable<DataState<List<CurrentWorkoutDto.ExerciseWithSets>>>

    fun getExerciseWithSets(exerciseId: Int): Single<DataState<CurrentWorkoutDto.ExerciseWithSets>>

    fun deleteExerciseWithSets(exercise: CurrentWorkoutDto.ExerciseWithSets): Single<DataState<Long>>

    fun updateExercise(exercise: CurrentWorkoutDto.Exercise): Single<DataState<Long>>

    fun updateSet(set: CurrentWorkoutDto.Set): Single<DataState<Long>>

    fun getSets(exerciseId: Int): Observable<DataState<List<CurrentWorkoutDto.Set>>>

    fun addSet(set: CurrentWorkoutDto.Set): Single<DataState<Long>>

    fun deleteSet(set: CurrentWorkoutDto.Set): Single<DataState<Long>>

    fun clearCash(): Single<DataState<Long>>
}
