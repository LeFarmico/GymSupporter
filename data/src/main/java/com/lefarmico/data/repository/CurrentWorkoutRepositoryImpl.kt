package com.lefarmico.data.repository

import com.lefarmico.data.db.CurrentWorkoutDataBase
import com.lefarmico.data.db.entity.CurrentWorkoutData
import com.lefarmico.data.extensions.dataStateActionResolver
import com.lefarmico.data.extensions.dataStateNullResolver
import com.lefarmico.data.extensions.dataStateResolver
import com.lefarmico.data.mapper.toData
import com.lefarmico.data.mapper.toDto
import com.lefarmico.data.mapper.toDtoExWithSets
import com.lefarmico.data.mapper.toDtoSet
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.lang.Exception
import javax.inject.Inject

class CurrentWorkoutRepositoryImpl @Inject constructor(
    private val dataBase: CurrentWorkoutDataBase
) : CurrentWorkoutRepository {

    override fun getExercisesWithSets(): Single<DataState<List<CurrentWorkoutDto.ExerciseWithSets>>> {
        return Single.create<DataState<List<CurrentWorkoutDto.ExerciseWithSets>>> {
            it.onSuccess(
                dataStateResolver(dataBase.getExercises().toDtoExWithSets())
            )
        }.doOnError { DataState.Error(it as Exception) }
    }

    override fun addExercise(exercise: CurrentWorkoutDto.Exercise): Single<DataState<Long>> {
        return Single.create<DataState<Long>> { emitter ->
            emitter.onSuccess(
                dataStateActionResolver {
                    val exerciseData = CurrentWorkoutData.Exercise(
                        libraryId = exercise.libraryId,
                        title = exercise.title
                    )
                    return@dataStateActionResolver dataBase.insertExercise(
                        CurrentWorkoutData.ExerciseWithSets(exerciseData)
                    )
                }
            )
        }.doOnError { DataState.Error(it as Exception) }
    }

    override fun deleteExercise(exerciseId: Int): Single<DataState<Long>> {
        return Single.create<DataState<Long>> { emitter ->
            emitter.onSuccess(
                dataStateNullResolver(dataBase.deleteExercise(exerciseId)!!)
            )
        }.doOnError { DataState.Error(it as Exception) }
    }

    override fun addSet(set: CurrentWorkoutDto.Set): Single<DataState<Long>> {
        return Single.create<DataState<Long>> { emitter ->
            emitter.onSuccess(
                dataStateNullResolver(dataBase.insertSet(set.toData())!!)
            )
        }.doOnError { DataState.Error(it as Exception) }
    }

    override fun deleteSet(set: CurrentWorkoutDto.Set): Single<DataState<Long>> {
        return Single.create<DataState<Long>> { emitter ->
            emitter.onSuccess(
                dataStateNullResolver(dataBase.deleteSetFromExercise(set.toData())!!)
            )
        }.doOnError { DataState.Error(it as Exception) }
    }

    override fun deleteLastSet(exerciseId: Int): Single<DataState<Long>> {
        return Single.create<DataState<Long>> { emitter ->
            emitter.onSuccess(
                dataStateNullResolver(dataBase.deleteLastSet(exerciseId)!!)
            )
        }.doOnError { DataState.Error(it as Exception) }
    }

    override fun getExerciseWithSets(exerciseId: Int): Single<DataState<CurrentWorkoutDto.ExerciseWithSets>> {
        return Single.create<DataState<CurrentWorkoutDto.ExerciseWithSets>> { emitter ->
            emitter.onSuccess(
                dataStateActionResolver {
                    dataBase.getExercise(exerciseId)!!.toDto()
                }
            )
        }.doOnError { DataState.Error(it as Exception) }
    }

    override fun getSets(exerciseId: Int): Single<DataState<List<CurrentWorkoutDto.Set>>> {
        return Single.create<DataState<List<CurrentWorkoutDto.Set>>> { emitter ->
            emitter.onSuccess(
                dataStateNullResolver(dataBase.getSets(exerciseId)!!.toDtoSet())
            )
        }.doOnError { DataState.Error(it as Exception) }
    }

    override fun clearCash() {
        dataBase.clearData()
    }
}
