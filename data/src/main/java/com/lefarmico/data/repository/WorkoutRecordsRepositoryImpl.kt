package com.lefarmico.data.repository

import com.lefarmico.data.db.dao.WorkoutRecordsDao
import com.lefarmico.data.mapper.toData
import com.lefarmico.data.mapper.toDto
import com.lefarmico.data.mapper.toExerciseListData
import com.lefarmico.data.mapper.toExerciseListDto
import com.lefarmico.data.mapper.toSetListData
import com.lefarmico.data.mapper.toSetListDto
import com.lefarmico.data.mapper.toWorkoutListDto
import com.lefarmico.data.mapper.toWorkoutWithExercisesAndSetsDto
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class WorkoutRecordsRepositoryImpl @Inject constructor(
    private val dao: WorkoutRecordsDao
) : WorkoutRecordsRepository {

    override fun addWorkout(workout: WorkoutRecordsDto.Workout): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertWorkout(workout.toData())
            it.onSuccess(DataState.Success(data))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun updateWorkout(workout: WorkoutRecordsDto.Workout): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            dao.updateWorkout(workout.toData())
            it.onSuccess(DataState.Success(1))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun deleteWorkout(workout: WorkoutRecordsDto.Workout): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            dao.deleteWorkout(workout.toData())
            it.onSuccess(DataState.Success(1))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getWorkouts(): Observable<DataState<List<WorkoutRecordsDto.Workout>>> {
        return dao.getWorkoutRecords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                if (data.isNotEmpty()) {
                    DataState.Success(data.toWorkoutListDto())
                } else {
                    DataState.Empty
                }
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getWorkout(workoutId: Int): Observable<DataState<WorkoutRecordsDto.Workout>> {
        return dao.getWorkout(workoutId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                DataState.Success(data.toDto()) as DataState<WorkoutRecordsDto.Workout>
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addExercise(exercise: WorkoutRecordsDto.Exercise): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertSet(exercise.toData())
            it.onSuccess(DataState.Success(data))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun updateExercise(exercise: WorkoutRecordsDto.Exercise): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            dao.updateExercise(exercise.toData())
            it.onSuccess(DataState.Success(1))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun deleteExercise(exercise: WorkoutRecordsDto.Exercise): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            dao.deleteExercise(exercise.toData())
            it.onSuccess(DataState.Success(1))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getExercises(workoutId: Int): Observable<DataState<List<WorkoutRecordsDto.Exercise>>> {
        return dao.getExerciseRecords(workoutId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                if (data.isNotEmpty()) {
                    DataState.Success(data.toExerciseListDto())
                } else {
                    DataState.Empty
                }
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getExercise(exerciseId: Int): Observable<DataState<WorkoutRecordsDto.Exercise>> {
        return dao.getExercise(exerciseId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                DataState.Success(data.toDto()) as DataState<WorkoutRecordsDto.Exercise>
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addSet(set: WorkoutRecordsDto.Set): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertSet(set.toData())
            it.onSuccess(DataState.Success(data))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun updateSet(set: WorkoutRecordsDto.Set): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            dao.updateSet(set.toData())
            it.onSuccess(DataState.Success(1))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun deleteSet(set: WorkoutRecordsDto.Set): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            dao.deleteSet(set.toData())
            it.onSuccess(DataState.Success(1))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getSets(exerciseId: Int): Observable<DataState<List<WorkoutRecordsDto.Set>>> {
        return dao.getSetRecords(exerciseId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                if (data.isNotEmpty()) {
                    DataState.Success(data.toSetListDto())
                } else {
                    DataState.Empty
                }
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getSet(setId: Int): Observable<DataState<WorkoutRecordsDto.Set>> {
        return dao.getSet(setId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                DataState.Success(data.toDto()) as DataState<WorkoutRecordsDto.Set>
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getWorkoutWithExerciseAnsSets(workoutId: Int):
        Observable<DataState<WorkoutRecordsDto.WorkoutWithExercisesAndSets>> {
        return dao.getWorkoutWithExerciseAnsSets(workoutId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                DataState.Success(data.toDto())
                    as DataState<WorkoutRecordsDto.WorkoutWithExercisesAndSets>
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getWorkoutsWithExerciseAnsSets():
        Observable<DataState<List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>> {
        return dao.getWorkoutsWithExerciseAnsSets()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                DataState.Success(data.toWorkoutWithExercisesAndSetsDto())
                    as DataState<List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addSetList(setList: List<WorkoutRecordsDto.Set>): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertSetList(setList.toSetListData())
            it.onSuccess(DataState.Success(1))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addExerciseList(exerciseList: List<WorkoutRecordsDto.Exercise>): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertExerciseList(exerciseList.toExerciseListData())
            it.onSuccess(DataState.Success(1))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }
}
