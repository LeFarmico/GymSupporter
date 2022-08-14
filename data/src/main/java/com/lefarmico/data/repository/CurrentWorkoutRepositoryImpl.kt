package com.lefarmico.data.repository

import com.lefarmico.data.db.CurrentWorkoutDataBase
import com.lefarmico.data.db.entity.CurrentWorkoutData
import com.lefarmico.data.extensions.dataStateResolver
import com.lefarmico.data.mapper.toData
import com.lefarmico.data.mapper.toDto
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CurrentWorkoutRepositoryImpl @Inject constructor(
    private val dataBase: CurrentWorkoutDataBase
) : CurrentWorkoutRepository {

    override fun getExercisesWithSets(): Single<DataState<List<CurrentWorkoutDto.ExerciseWithSets>>> {
        return dataBase.getExercises()
            .map { data -> dataStateResolver { data.toDto() } }
    }

    override fun addExercise(exercise: CurrentWorkoutDto.Exercise): Single<DataState<Int>> {
        val curExData = CurrentWorkoutData.ExerciseWithSets(exercise.toData())
        return dataBase.insertExercise(curExData)
            .map { data -> dataStateResolver { data } }
    }

    override fun addExercises(exerciseList: List<CurrentWorkoutDto.ExerciseWithSets>): Single<DataState<Int>> {
        return dataBase.insertExercises(exerciseList.toData())
            .map { data -> dataStateResolver { data } }
    }

    override fun deleteExercise(exerciseId: Int): Single<DataState<Int>> {
        return dataBase.deleteExercise(exerciseId)
            .map { data -> dataStateResolver { data } }
    }

    override fun addSet(set: CurrentWorkoutDto.Set): Single<DataState<Int>> {
        return dataBase.insertSet(set.toData())
            .map { data -> dataStateResolver { data } }
    }

    override fun deleteSet(set: CurrentWorkoutDto.Set): Single<DataState<Int>> {
        return dataBase.deleteSetFromExercise(set.toData())
            .map { data -> dataStateResolver { data } }
    }

    override fun deleteLastSet(exerciseId: Int): Single<DataState<Int>> {
        return dataBase.deleteLastSet(exerciseId)
            .map { data -> dataStateResolver { data } }
    }

    override fun getExerciseWithSets(exerciseId: Int): Single<DataState<CurrentWorkoutDto.ExerciseWithSets>> {
        return dataBase.getExercise(exerciseId)
            .map { data -> dataStateResolver { data.toDto() } }
    }

    override fun setUpdate(isUpdate: Boolean): Single<Boolean> {
        return dataBase.setUpdate(isUpdate)
    }

    override fun isUpdateMode(): Single<Boolean> {
        return dataBase.isUpdate()
    }

    override fun getSets(exerciseId: Int): Single<DataState<List<CurrentWorkoutDto.Set>>> {
        return dataBase.getSets(exerciseId)
            .map { data -> dataStateResolver { data.toDto() } }
    }

    override fun clearCache() {
        dataBase.clearData()
    }
}
