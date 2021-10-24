package com.lefarmico.data.repository

import com.lefarmico.data.db.CurrentWorkoutDataBase
import com.lefarmico.data.db.entity.CurrentWorkoutData
import com.lefarmico.data.mapper.toData
import com.lefarmico.data.mapper.toDto
import com.lefarmico.data.mapper.toExerciseWithSetsDto
import com.lefarmico.data.mapper.toSetDto
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
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
                it.onSuccess(DataState.Success(data.toExerciseWithSetsDto()))
            } else {
                it.onSuccess(DataState.Empty)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getSets(exerciseId: Int): Single<DataState<List<CurrentWorkoutDto.Set>>> {
        val exercise = dataBase.exerciseWithSetsList.find { it.exercise.id == exerciseId }
        return Single.create<DataState<List<CurrentWorkoutDto.Set>>> {
            if (exercise != null) {
                it.onSuccess(
                    DataState.Success(exercise.setList.toSetDto())
                        as DataState<List<CurrentWorkoutDto.Set>>
                )
            } else {
                it.onSuccess(DataState.Empty)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addExercise(exercise: CurrentWorkoutDto.Exercise): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            dataBase.insertExerciseWithSets(
                CurrentWorkoutData.ExerciseWithSets(
                    CurrentWorkoutData.Exercise.Builder()
                        .setTitle(exercise.title)
                        .setLibraryId(exercise.libraryId)
                        .build()
                )
            )
            it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
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
                it.onError(NullPointerException())
            }
            it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun deleteExercise(exerciseId: Int): Single<DataState<Long>> {
        val exerciseWithSets = dataBase.exerciseWithSetsList.find { it.exercise.id == exerciseId }
        return Single.create<DataState<Long>> { emitter ->
            if (exerciseWithSets != null) {
                dataBase.exerciseWithSetsList.remove(exerciseWithSets)
                emitter.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
            } else {
                emitter.onError(NullPointerException())
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun deleteSet(set: CurrentWorkoutDto.Set): Single<DataState<Long>> {
        val exerciseWithSets = dataBase.exerciseWithSetsList.find { it.exercise.id == set.exerciseId }
        return Single.create<DataState<Long>> {
            if (exerciseWithSets != null) {
                exerciseWithSets.setList - set
                it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
            } else {
                it.onError(NullPointerException())
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun deleteLastSet(exerciseId: Int): Single<DataState<Long>> {
        val exerciseWithSets = dataBase.exerciseWithSetsList.find { it.exercise.id == exerciseId }
        return Single.create<DataState<Long>> {
            if (exerciseWithSets != null) {
                val lastSet = exerciseWithSets.setList[exerciseWithSets.setList.size - 1]
                exerciseWithSets.setList.remove(lastSet)
                if (exerciseWithSets.setList.isEmpty()) {
                    dataBase.exerciseWithSetsList.remove(exerciseWithSets)
                }
                it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
            } else {
                it.onError(NullPointerException())
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getExerciseWithSets(exerciseId: Int): Single<DataState<CurrentWorkoutDto.ExerciseWithSets>> {
        return Single.create<DataState<CurrentWorkoutDto.ExerciseWithSets>> { emitter ->
            val exercise = dataBase.exerciseWithSetsList.find { it.exercise.id == exerciseId }
            if (exercise != null) {
                emitter.onSuccess(
                    DataState.Success(exercise.toDto())
                )
            } else {
                emitter.onError(NullPointerException())
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun clearCash() {
        dataBase.exerciseWithSetsList.clear()
    }
}
