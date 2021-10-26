package com.lefarmico.data.repository

import com.lefarmico.data.db.dao.WorkoutRecordsDao
import com.lefarmico.data.mapper.toData
import com.lefarmico.data.mapper.toDto
import com.lefarmico.data.mapper.toSetListData
import com.lefarmico.data.mapper.toWorkoutWithExercisesAndSetsDto
import com.lefarmico.data.utils.setExerciseId
import com.lefarmico.data.utils.setWorkoutId
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class WorkoutRecordsRepositoryImpl @Inject constructor(
    private val dao: WorkoutRecordsDao
) : WorkoutRecordsRepository {

    override fun getWorkoutWithExerciseAnsSets(workoutId: Int):
        Observable<DataState<WorkoutRecordsDto.WorkoutWithExercisesAndSets>> {
        return dao.getWorkoutWithExerciseAnsSets(workoutId)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
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
            .observeOn(Schedulers.io())
            .map { data ->
                DataState.Success(data.toWorkoutWithExercisesAndSetsDto())
                    as DataState<List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
                throw (it)
            }
    }

    override fun addWorkoutWithExAndSets(
        workoutWithExercisesAndSets: WorkoutRecordsDto.WorkoutWithExercisesAndSets
    ): Single<DataState<String>> {
        return Single.create<Long> {
            it.onSuccess(dao.insertWorkout(workoutWithExercisesAndSets.workout.toData()))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .onErrorReturn {
                throw (it)
            }
            .map { workoutId ->
                val exerciseList = workoutWithExercisesAndSets
                    .exerciseWithSetsList
                    .setWorkoutId(workoutId.toInt())

                for (i in exerciseList.indices) {
                    val exercise = exerciseList[i].exercise.toData()
                    val exerciseId = dao.insertExercise(exercise)
                    val setList = exerciseList[i].setList.setExerciseId(exerciseId.toInt())
                    dao.insertSets(setList.toSetListData())
                }
                DataState.Success("New workout successfully added.")
            }
    }

    override fun deleteWorkoutWithExAndSets(workoutId: Int): Single<DataState<String>> {
        TODO("Not yet implemented")
    }

    override fun updateWorkoutWithExAndSets(
        workoutWithExercisesAndSets: WorkoutRecordsDto.WorkoutWithExercisesAndSets
    ): Single<DataState<String>> {
        TODO("Not yet implemented")
    }
}
