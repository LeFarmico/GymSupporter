package com.lefarmico.data.repository

import android.util.Log
import com.lefarmico.data.db.dao.WorkoutRecordsDao
import com.lefarmico.data.extensions.dataStateActionResolver
import com.lefarmico.data.extensions.dataStateResolver
import com.lefarmico.data.mapper.toData
import com.lefarmico.data.mapper.toDto
import com.lefarmico.data.mapper.toSetListData
import com.lefarmico.data.mapper.toWorkoutWithExercisesAndSetsDto
import com.lefarmico.data.utils.setExerciseId
import com.lefarmico.data.utils.setWorkoutId
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.lang.Exception
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class WorkoutRecordsRepositoryImpl @Inject constructor(
    private val dao: WorkoutRecordsDao
) : WorkoutRecordsRepository {

    override fun getWorkoutWithExerciseAnsSets(workoutId: Int):
        Single<DataState<WorkoutRecordsDto.WorkoutWithExercisesAndSets>> {
        return dao.getWorkoutWithExerciseAnsSets(workoutId)
            .doOnSubscribe { DataState.Loading }
            .doOnError { DataState.Error(it as Exception) }
            .map { data -> DataState.Success(data.toDto()) }
    }

    override fun getWorkoutsWithExerciseAnsSets():
        Single<DataState<List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>> {
        return dao.getWorkoutsWithExerciseAnsSets()
            .doOnSubscribe { DataState.Loading }
            .doOnError { DataState.Error(it as Exception) }
            .map { data -> dataStateResolver(data.toWorkoutWithExercisesAndSetsDto()) }
    }

    // TODO упросить
    override fun addWorkoutWithExAndSets(
        workoutWithExercisesAndSets: WorkoutRecordsDto.WorkoutWithExercisesAndSets
    ): Single<DataState<Long>> {
        return Single.create<DataState<Long>> { emitter ->
            emitter.onSuccess(
                dataStateActionResolver {
                    val workoutId = dao.insertWorkout(workoutWithExercisesAndSets.workout.toData())
                    val exerciseList = workoutWithExercisesAndSets
                        .exerciseWithSetsList
                        .setWorkoutId(workoutId.toInt())

                    exerciseList.forEach { exerciseWithSets ->
                        val exercise = exerciseWithSets.exercise.toData()
                        val exerciseId = dao.insertExercise(exercise)
                        val setList = exerciseWithSets.setList.setExerciseId(exerciseId.toInt())
                        dao.insertSets(setList.toSetListData())
                    }
                    return@dataStateActionResolver workoutId
                }
            )
        }.doOnError { e -> DataState.Error(e as Exception) }
    }

    override fun deleteWorkoutWithExAndSets(workoutId: Int): Single<DataState<Int>> {
        Log.e("TAGGG", "$workoutId !!!")
        return dao.getWorkoutWithExerciseAnsSets(workoutId)
            .doOnError { e -> DataState.Error(e as Exception) }
            .map { data -> dataStateActionResolver { dao.deleteWorkout(data.workout) } }
    }

    override fun updateWorkoutWithExAndSets(
        workoutWithExercisesAndSets: WorkoutRecordsDto.WorkoutWithExercisesAndSets
    ): Single<DataState<String>> {
        TODO("Not yet implemented")
    }

    override fun getWorkoutWithExerciseAndSetsByDate(date: LocalDateTime):
        Single<DataState<List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>> {
        return dao.getWorkoutsWithExerciseAnsSetsByDate(date)
            .doOnSubscribe { DataState.Loading }
            .doOnError { DataState.Error(it as Exception) }
            .map { data -> dataStateResolver(data.toWorkoutWithExercisesAndSetsDto()) }
    }
}
