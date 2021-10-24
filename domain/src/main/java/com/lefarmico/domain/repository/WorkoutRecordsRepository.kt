package com.lefarmico.domain.repository

import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface WorkoutRecordsRepository : BaseRepository {

    fun getWorkoutWithExerciseAnsSets(workoutId: Int):
        Observable<DataState<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>

    fun getWorkoutsWithExerciseAnsSets():
        Observable<DataState<List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>>

    fun addWorkoutWithExAndSets(
        workoutWithExercisesAndSets: WorkoutRecordsDto.WorkoutWithExercisesAndSets
    ): Single<DataState<Boolean>>

    fun deleteWorkoutWithExAndSets(workoutId: Int): Single<DataState<Boolean>>

    fun updateWorkoutWithExAndSets(
        workoutWithExercisesAndSets: WorkoutRecordsDto.WorkoutWithExercisesAndSets
    ): Single<DataState<Boolean>>
}
