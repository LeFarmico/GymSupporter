package com.lefarmico.data.repository

import com.lefarmico.data.BuildConfig
import com.lefarmico.data.db.CurrentWorkoutDataBase
import com.lefarmico.data.db.entity.CurrentWorkoutData
import com.lefarmico.data.mapper.toData
import com.lefarmico.data.mapper.toDto
import com.lefarmico.data.mapper.toDtoExWithSets
import com.lefarmico.data.mapper.toDtoSet
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.lang.Exception
import java.lang.NullPointerException
import javax.inject.Inject

class CurrentWorkoutRepositoryImpl @Inject constructor(
    private val dataBase: CurrentWorkoutDataBase
) : CurrentWorkoutRepository {

    override fun getExercisesWithSets(): Single<DataState<List<CurrentWorkoutDto.ExerciseWithSets>>> {
        val data = dataBase.exerciseWithSetsList
        return Single.create<DataState<List<CurrentWorkoutDto.ExerciseWithSets>>> {
            if (data.isNotEmpty()) {
                it.onSuccess(DataState.Success(data.toDtoExWithSets()))
            } else {
                it.onSuccess(DataState.Empty)
            }
        }
            .onErrorReturn {
                if (BuildConfig.DEBUG) {
                    throw (it)
                }
                DataState.Error(it as Exception)
            }
    }

    override fun getSets(exerciseId: Int): Single<DataState<List<CurrentWorkoutDto.Set>>> {
        val exercise = dataBase.exerciseWithSetsList.find { it.exercise.id == exerciseId }
        return Single.create<DataState<List<CurrentWorkoutDto.Set>>> {
            if (exercise != null) {
                it.onSuccess(
                    DataState.Success(exercise.setList.toDtoSet())
                        as DataState<List<CurrentWorkoutDto.Set>>
                )
            } else {
                it.onSuccess(DataState.Empty)
            }
        }
            .onErrorReturn {
                if (BuildConfig.DEBUG) {
                    throw (it)
                }
                DataState.Error(it as Exception)
            }
    }

    override fun addExercise(exercise: CurrentWorkoutDto.Exercise): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val exerciseId = dataBase.insertExerciseWithSets(
                CurrentWorkoutData.ExerciseWithSets(
                    CurrentWorkoutData.Exercise.Builder()
                        .setTitle(exercise.title)
                        .setLibraryId(exercise.libraryId)
                        .build()
                )
            )
            it.onSuccess(DataState.Success(exerciseId))
        }
            .onErrorReturn {
                if (BuildConfig.DEBUG) {
                    throw (it)
                }
                DataState.Error(it as Exception)
            }
    }

    override fun addSet(set: CurrentWorkoutDto.Set): Single<DataState<Long>> {
        val exerciseWithSets = dataBase.exerciseWithSetsList.find { it.exercise.id == set.exerciseId }
        return Single.create<DataState<Long>> {
            if (exerciseWithSets != null) {
                exerciseWithSets.setList.add(set.toData())
                it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
            } else {
                if (BuildConfig.DEBUG) {
                    throw (NullPointerException("That field is not exist"))
                }
                it.onError(NullPointerException())
            }
            it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
        }
            .onErrorReturn {
                if (BuildConfig.DEBUG) {
                    throw (it)
                }
                DataState.Error(it as Exception)
            }
    }

    override fun deleteExercise(exerciseId: Int): Single<DataState<String>> {
        val exerciseWithSets = dataBase.exerciseWithSetsList.find { it.exercise.id == exerciseId }
        return Single.create<DataState<String>> { emitter ->
            if (exerciseWithSets != null) {
                dataBase.exerciseWithSetsList.remove(exerciseWithSets)
                emitter.onSuccess(DataState.Success("Exercise deleted"))
            } else {
                if (BuildConfig.DEBUG) {
                    throw (NullPointerException("That field is not exist"))
                }
                emitter.onError(NullPointerException())
            }
        }
            .onErrorReturn {
                if (BuildConfig.DEBUG) {
                    throw (it)
                }
                DataState.Error(it as Exception)
            }
    }

    override fun deleteSet(set: CurrentWorkoutDto.Set): Single<DataState<String>> {
        val exerciseWithSets = dataBase.exerciseWithSetsList.find { it.exercise.id == set.exerciseId }
        return Single.create<DataState<String>> {
            if (exerciseWithSets != null) {
                exerciseWithSets.setList - set
                it.onSuccess(DataState.Success("Set deleted"))
            } else {
                if (BuildConfig.DEBUG) {
                    throw (NullPointerException("That field is not exist"))
                }
                it.onError(NullPointerException())
            }
        }
            .onErrorReturn {
                if (BuildConfig.DEBUG) {
                    throw (it)
                }
                DataState.Error(it as Exception)
            }
    }

    override fun deleteLastSet(exerciseId: Int): Single<DataState<String>> {
        val exerciseWithSets = dataBase.exerciseWithSetsList.find { it.exercise.id == exerciseId }
        return Single.create<DataState<String>> {
            if (exerciseWithSets != null) {
                val lastSet = exerciseWithSets.setList[exerciseWithSets.setList.size - 1]
                exerciseWithSets.setList.remove(lastSet)
                if (exerciseWithSets.setList.isEmpty()) {
                    dataBase.exerciseWithSetsList.remove(exerciseWithSets)
                }
                it.onSuccess(DataState.Success("Set deleted"))
            } else {
                if (BuildConfig.DEBUG) {
                    throw (NullPointerException("That field is not exist"))
                }
                it.onError(NullPointerException())
            }
        }
            .onErrorReturn {
                if (BuildConfig.DEBUG) {
                    throw (it)
                }
                DataState.Error(it as Exception)
            }
    }

    override fun getExerciseWithSets(exerciseId: Int): Single<DataState<CurrentWorkoutDto.ExerciseWithSets>> {
        return Single.create<DataState<CurrentWorkoutDto.ExerciseWithSets>> { emitter ->
            val exercise = dataBase.exerciseWithSetsList.find { it.exercise.id == exerciseId }
            if (exercise != null) {
                val dto = exercise.toDto()
                emitter.onSuccess(DataState.Success(dto))
            } else {
                if (BuildConfig.DEBUG) {
                    throw (NullPointerException("That field is not exist"))
                }
                emitter.onError(NullPointerException())
            }
        }
            .onErrorReturn {
                if (BuildConfig.DEBUG) {
                    throw (it)
                }
                DataState.Error(it as Exception)
            }
    }

    override fun clearCash() {
        dataBase.exerciseWithSetsList.clear()
    }
}
