package com.lefarmico.domain.repository

import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface WorkoutRecordsRepository : BaseRepository {

    fun addWorkout(workout: WorkoutRecordsDto.Workout): Single<DataState<Long>>

    fun updateWorkout(workout: WorkoutRecordsDto.Workout): Single<DataState<Long>>

    fun deleteWorkout(workout: WorkoutRecordsDto.Workout): Single<DataState<Long>>

    fun getWorkouts(): Observable<DataState<List<WorkoutRecordsDto.Workout>>>

    fun getWorkout(workoutId: Int): Observable<DataState<WorkoutRecordsDto.Workout>>
}
