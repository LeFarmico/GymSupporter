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

    fun addExercise(exercise: WorkoutRecordsDto.Exercise): Single<DataState<Long>>

    fun updateExercise(exercise: WorkoutRecordsDto.Exercise): Single<DataState<Long>>

    fun deleteExercise(exercise: WorkoutRecordsDto.Exercise): Single<DataState<Long>>

    fun getExercises(workoutId: Int): Observable<DataState<List<WorkoutRecordsDto.Exercise>>>

    fun getExercise(exerciseId: Int): Observable<DataState<WorkoutRecordsDto.Exercise>>

    fun addSet(set: WorkoutRecordsDto.Set): Single<DataState<Long>>

    fun updateSet(set: WorkoutRecordsDto.Set): Single<DataState<Long>>

    fun deleteSet(set: WorkoutRecordsDto.Set): Single<DataState<Long>>

    fun getSets(exerciseId: Int): Observable<DataState<List<WorkoutRecordsDto.Set>>>

    fun getSet(setId: Int): Observable<DataState<WorkoutRecordsDto.Set>>

    fun getWorkoutWithExerciseAnsSets(workoutId: Int):
        Observable<DataState<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>

    fun getWorkoutsWithExerciseAnsSets():
        Observable<DataState<List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>>

    fun addSetList(setList: List<WorkoutRecordsDto.Set>): Single<DataState<Long>>

    fun addExerciseList(exerciseList: List<WorkoutRecordsDto.Exercise>): Single<DataState<Long>>
}
