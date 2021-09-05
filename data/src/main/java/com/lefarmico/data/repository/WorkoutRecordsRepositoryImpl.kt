package com.lefarmico.data.repository

import com.lefarmico.data.db.dao.WorkoutRecordsDao
import com.lefarmico.data.utils.toData
import com.lefarmico.data.utils.toWorkoutListDto
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception

class WorkoutRecordsRepositoryImpl(
    private val dao: WorkoutRecordsDao
) : WorkoutRecordsRepository {
    
    override fun addWorkout(workout: WorkoutRecordsDto.Workout): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertWorkoutNote(workout.toData())
            it.onSuccess(DataState.Success(data))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getWorkouts(): Observable<DataState<List<WorkoutRecordsDto.Workout>>> {
        return dao.getWorkoutNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                if (data.isNotEmpty()) {
                    DataState.Success(data.toWorkoutListDto())
                } else {
                    DataState.Loading
                }
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }
}
