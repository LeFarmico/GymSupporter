package com.lefarmico.data.repository

import com.lefarmico.data.db.dao.CurrentWorkoutDao
import com.lefarmico.data.mapper.toData
import com.lefarmico.data.mapper.toDto
import com.lefarmico.data.mapper.toExerciseWithSetsDto
import com.lefarmico.data.mapper.toSetData
import com.lefarmico.data.mapper.toSetDto
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class CurrentWorkoutRepositoryImpl @Inject constructor(
    private val dao: CurrentWorkoutDao
) : CurrentWorkoutRepository {

    override fun addExercise(exercise: CurrentWorkoutDto.Exercise): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertExercise(exercise.toData())
            it.onSuccess(DataState.Success(data))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getExercisesWithSets(): Observable<DataState<List<CurrentWorkoutDto.ExerciseWithSets>>> {
        return dao.getExercisesWithSets()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                if (data.isNotEmpty()) {
                    DataState.Success(data.toExerciseWithSetsDto())
                } else {
                    DataState.Empty
                }
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getExerciseWithSets(exerciseId: Int): Single<DataState<CurrentWorkoutDto.ExerciseWithSets>> {
        return dao.getExerciseWithSets(exerciseId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                DataState.Success(data.toDto()) as DataState<CurrentWorkoutDto.ExerciseWithSets>
            }
            .onErrorReturn { err ->
                DataState.Error(err as Exception)
            }
    }

    override fun deleteExerciseWithSets(exercise: CurrentWorkoutDto.ExerciseWithSets): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            dao.deleteExercise(exercise.exercise.toData())
            dao.deleteSets(exercise.setList.toSetData())
            it.onSuccess(DataState.Success(1))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun updateExercise(exercise: CurrentWorkoutDto.Exercise): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val res = dao.updateExercise(exercise.toData())
            it.onSuccess(DataState.Success(res.toLong()))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun updateSet(set: CurrentWorkoutDto.Set): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val res = dao.updateSet(set.toData())
            it.onSuccess(DataState.Success(res.toLong()))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getSets(exerciseId: Int): Observable<DataState<List<CurrentWorkoutDto.Set>>> {
        return dao.getSets(exerciseId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                if (data.isNotEmpty()) {
                    DataState.Success(data.toSetDto())
                } else {
                    DataState.Empty
                }
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addSet(set: CurrentWorkoutDto.Set): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val res = dao.insertSet(set.toData())
            it.onSuccess(DataState.Success(res))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun deleteSet(set: CurrentWorkoutDto.Set): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val res = dao.deleteSet(set.toData())
            it.onSuccess(DataState.Success(res.toLong()))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun clearCash(): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            dao.clearExerciseData()
            dao.clearSetData()
            it.onSuccess(DataState.Success(1))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }
//    override fun getExercisesWithSets(): Observable<DataState<List<WorkoutRecordsDto.ExerciseWithSets>>> {
//        val data = dataBase.exerciseList
//        return Observable.create<DataState<List<WorkoutRecordsDto.ExerciseWithSets>>> {
//            if (data.isNotEmpty()) {
//                it.onNext(DataState.Success(data))
//            } else {
//                it.onNext(DataState.Empty)
//            }
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .onErrorReturn {
//                DataState.Error(it as Exception)
//            }
//    }
//
//    override fun getSets(exerciseId: Int): Observable<DataState<List<WorkoutRecordsDto.Set>>> {
//        val exercise = dataBase.exerciseList.find { it.exercise.id == exerciseId }
//        return Observable.create<DataState<List<WorkoutRecordsDto.Set>>> {
//            if (exercise != null) {
//                it.onNext(DataState.Success(exercise) as DataState<List<WorkoutRecordsDto.Set>>)
//            } else {
//                it.onNext(DataState.Empty)
//            }
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .onErrorReturn {
//                DataState.Error(it as Exception)
//            }
//    }
//
//    override fun addExercise(exercise: WorkoutRecordsDto.ExerciseWithSets): Single<DataState<Long>> {
//        return Single.create<DataState<Long>> {
//            dataBase.exerciseList.add(exercise)
//            it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .onErrorReturn {
//                DataState.Error(it as Exception)
//            }
//    }
//
//    override fun addSet(set: WorkoutRecordsDto.Set): Single<DataState<Long>> {
//        val curExerciseWithSets = dataBase.exerciseList.find { it.exercise.id == set.exerciseId }
//        val curExerciseId = dataBase.exerciseList.indexOfFirst { it.exercise.id == set.exerciseId }
//        val curExercise = dataBase.exerciseList[curExerciseId]
//        val setList = mutableListOf<WorkoutRecordsDto.Set>()
//        setList.addAll(curExercise.noteSetList)
//        setList.add(set)
//        return Single.create<DataState<Long>> {
//            if (curExerciseId >= 0) {
//                dataBase.exerciseList[curExerciseId] = WorkoutRecordsDto.ExerciseWithSets(
//                    id = curExercise.id,
//                    exerciseName = curExercise.exerciseName,
//                    noteSetList = setList
//                )
//                it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
//            } else {
//                it.onError(NullPointerException())
//            }
//            it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .onErrorReturn {
//                DataState.Error(it as Exception)
//            }
//    }
//
//    override fun deleteExercise(exercise: WorkoutRecordsDto.Exercise): Single<DataState<Long>> {
//        return Single.create<DataState<Long>> {
//            dataBase.exerciseList.remove(exercise)
//            it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .onErrorReturn {
//                DataState.Error(it as Exception)
//            }
//    }
//
//    override fun deleteSet(set: WorkoutRecordsDto.Set): Single<DataState<Long>> {
//        val curExerciseId = dataBase.exerciseList.indexOfFirst { it.id == set.exerciseId }
//        val curExercise = dataBase.exerciseList[curExerciseId]
//        val setList = mutableListOf<WorkoutRecordsDto.Set>()
//        setList.addAll(curExercise.noteSetList)
//        setList.remove(set)
//        return Single.create<DataState<Long>> {
//            if (curExerciseId >= 0) {
//                dataBase.exerciseList[curExerciseId] = WorkoutRecordsDto.Exercise(
//                    id = curExercise.id,
//                    exerciseName = curExercise.exerciseName,
//                    noteSetList = setList
//                )
//                it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
//            } else {
//                it.onError(NullPointerException())
//            }
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .onErrorReturn {
//                DataState.Error(it as Exception)
//            }
//    }
//
//    override fun deleteLastSet(exerciseId: Int): Single<DataState<Long>> {
//        val curExerciseIndex = dataBase.exerciseList.indexOfFirst { it.id == exerciseId }
//        val exercise = dataBase.exerciseList[curExerciseIndex]
//        val setList = mutableListOf<WorkoutRecordsDto.Set>()
//        return Single.create<DataState<Long>> {
//            if (curExerciseIndex >= 0) {
//                setList.addAll(dataBase.exerciseList[curExerciseIndex].noteSetList)
//                setList.removeLast()
//                dataBase.exerciseList[curExerciseIndex] = WorkoutRecordsDto.Exercise(
//                    id = exercise.id,
//                    exerciseName = exercise.exerciseName,
//                    noteSetList = setList
//                )
//                it.onSuccess(DataState.Success(CurrentWorkoutDataBase.SUCCESS))
//            } else {
//                it.onError(NullPointerException())
//            }
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .onErrorReturn {
//                DataState.Error(it as Exception)
//            }
//    }
//
//    override fun getExercise(exerciseId: Int): Single<DataState<WorkoutRecordsDto.Exercise>> {
//        return Single.create<DataState<WorkoutRecordsDto.Exercise>> { emitter ->
//            val exercise = dataBase.exerciseList.find { it.id == exerciseId }
//            if (exercise != null) {
//                emitter.onSuccess(
//                    DataState.Success(exercise)
//                )
//            } else {
//                emitter.onError(NullPointerException())
//            }
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .onErrorReturn {
//                DataState.Error(it as Exception)
//            }
//    }
//
//    override fun clearCash() {
//        dataBase.exerciseList.clear()
//    }
}
