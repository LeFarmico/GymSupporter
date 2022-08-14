package com.lefarmico.domain.repository

import android.view.Display
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single

interface CurrentWorkoutRepository : BaseRepository {

    fun getExercisesWithSets(): Single<DataState<List<CurrentWorkoutDto.ExerciseWithSets>>>

    fun getSets(exerciseId: Int): Single<DataState<List<CurrentWorkoutDto.Set>>>

    fun addExercise(exercise: CurrentWorkoutDto.Exercise): Single<DataState<Int>>

    fun addExercises(exerciseList: List<CurrentWorkoutDto.ExerciseWithSets>): Single<DataState<Int>>

    fun addSet(set: CurrentWorkoutDto.Set): Single<DataState<Int>>

    fun deleteExercise(exerciseId: Int): Single<DataState<Int>>

    fun deleteSet(set: CurrentWorkoutDto.Set): Single<DataState<Int>>

    fun deleteLastSet(exerciseId: Int): Single<DataState<Int>>

    fun getExerciseWithSets(exerciseId: Int): Single<DataState<CurrentWorkoutDto.ExerciseWithSets>>

    fun setUpdate(isUpdate: Boolean): Single<Boolean>

    fun isUpdateMode(): Single<Boolean>

    fun clearCache()
}
