package com.lefarmico.data.repository

import com.lefarmico.data.db.CurrentWorkoutDataBase
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import java.lang.NullPointerException
import javax.inject.Inject

class CurrentWorkoutRepositoryImpl @Inject constructor(
    private val dataBase: CurrentWorkoutDataBase
) : CurrentWorkoutRepository {

    override fun getExercises(): Observable<DataState<List<WorkoutRecordsDto.Exercise>>> {
        val data = dataBase.exerciseList
        return Observable.create<DataState<List<WorkoutRecordsDto.Exercise>>> {
            if (data.isNotEmpty()) {
                it.onNext(DataState.Success(data))
            } else {
                it.onNext(DataState.Empty)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getSets(exerciseId: Int): Observable<DataState<List<WorkoutRecordsDto.Set>>> {
        val exercise = dataBase.exerciseList.find { it.id == exerciseId }
        return Observable.create<DataState<List<WorkoutRecordsDto.Set>>> {
            if (exercise != null) {
                it.onNext(DataState.Success(exercise.noteSetList) as DataState<List<WorkoutRecordsDto.Set>>)
            } else {
                it.onNext(DataState.Empty)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addExercise(exercise: WorkoutRecordsDto.Exercise): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            dataBase.exerciseList.add(exercise)
            it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addSet(set: WorkoutRecordsDto.Set): Single<DataState<Long>> {
        val exercise = dataBase.exerciseList.find { it.id == set.exerciseId }
        return Single.create<DataState<Long>> {
            if (exercise != null) {
                exercise.noteSetList.add(set)
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

    override fun deleteExercise(exercise: WorkoutRecordsDto.Exercise): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            dataBase.exerciseList.remove(exercise)
            it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun deleteSet(set: WorkoutRecordsDto.Set): Single<DataState<Long>> {
        val exercise = dataBase.exerciseList.find { it.id == set.exerciseId }
        return Single.create<DataState<Long>> {
            if (exercise != null) {
                exercise.noteSetList.remove(set)
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
        val exercise = dataBase.exerciseList.find { it.id == exerciseId }
        return Single.create<DataState<Long>> {
            if (exercise != null) {
                exercise.noteSetList.removeLast()
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

    override fun getExercise(exerciseId: Int): Single<DataState<WorkoutRecordsDto.Exercise>> {
        return Single.create<DataState<WorkoutRecordsDto.Exercise>> { emitter ->
            val exercise = dataBase.exerciseList.find { it.id == exerciseId }
            if (exercise != null) {
                emitter.onSuccess(
                    DataState.Success(exercise)
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
}
