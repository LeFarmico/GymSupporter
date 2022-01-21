package com.lefarmico.data.repository

import com.lefarmico.data.db.dao.WorkoutRecordsDao
import com.lefarmico.data.extensions.dataStateResolver
import com.lefarmico.data.mapper.toData
import com.lefarmico.data.mapper.toDto
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.lang.Exception
import java.time.LocalDate
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
            .map { data -> dataStateResolver { data.toDto() } }
    }

    override fun addWorkoutWithExAndSets(
        workoutWithExercisesAndSets: WorkoutRecordsDto.WorkoutWithExercisesAndSets
    ): Single<DataState<Long>> {
        return dao.insertWorkout(workoutWithExercisesAndSets.workout.toData())
            .map { workoutId ->
                dao.deleteExercises(workoutId.toInt())
                val exercises = workoutWithExercisesAndSets.exerciseWithSetsList.map { it.exercise.copy(workoutId = workoutId.toInt()) }
                val exIds = dao.insertExercises(exercises.toData())
                val setList = workoutWithExercisesAndSets.exerciseWithSetsList.map { it.setList }
                for (i in exIds.indices) {
                    val sets = setList[i].map { it.copy(exerciseId = exIds[i].toInt()) }
                    dao.insertSets(sets.toData())
                }
                dataStateResolver { workoutId }
            }.doOnError { DataState.Error(it as Exception) }
    }

    override fun deleteWorkoutWithExAndSets(workoutId: Int): Single<DataState<Int>> {
        return dao.getWorkoutWithExerciseAnsSets(workoutId)
            .doOnError { e -> DataState.Error(e as Exception) }
            .map { data -> dataStateResolver { dao.deleteWorkout(data.workout) } }
    }

    override fun getWorkoutWithExerciseAndSetsByDate(date: LocalDate):
        Single<DataState<List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>> {
        return dao.getWorkoutsWithExerciseAnsSetsByDate(date)
            .doOnSubscribe { DataState.Loading }
            .doOnError { DataState.Error(it as Exception) }
            .map { data -> dataStateResolver { data.toDto() } }
    }
}
