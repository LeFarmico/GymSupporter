package com.lefarmico.domain.repository

import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CurrentWorkoutRepository : BaseRepository {

    fun getExercises(): Observable<DataState<List<WorkoutRecordsDto.Exercise>>>

    fun getSets(exerciseId: Int): Observable<DataState<List<WorkoutRecordsDto.Set>>>

    fun addExercise(exercise: WorkoutRecordsDto.Exercise): Single<DataState<Long>>

    fun addSet(set: WorkoutRecordsDto.Set): Single<DataState<Long>>

    fun deleteExercise(exercise: WorkoutRecordsDto.Exercise): Single<DataState<Long>>

    fun deleteSet(set: WorkoutRecordsDto.Set): Single<DataState<Long>>

    fun deleteLastSet(exerciseId: Int): Single<DataState<Long>>

    fun getExercise(exerciseId: Int): Single<DataState<WorkoutRecordsDto.Exercise>>

    fun clearCash()
}
