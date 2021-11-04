package com.lefarmico.domain.repository

import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single

interface WorkoutRecordsRepository : BaseRepository {

    fun getWorkoutWithExerciseAnsSets(workoutId: Int):
        Single<DataState<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>

    fun getWorkoutsWithExerciseAnsSets():
        Single<DataState<List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>>

    fun addWorkoutWithExAndSets(
        workoutWithExercisesAndSets: WorkoutRecordsDto.WorkoutWithExercisesAndSets
    ): Single<DataState<Long>>

    fun deleteWorkoutWithExAndSets(workoutId: Int): Single<DataState<Int>>

    fun updateWorkoutWithExAndSets(
        workoutWithExercisesAndSets: WorkoutRecordsDto.WorkoutWithExercisesAndSets
    ): Single<DataState<String>>
}
