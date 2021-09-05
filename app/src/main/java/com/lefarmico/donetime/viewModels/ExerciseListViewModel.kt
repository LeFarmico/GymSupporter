package com.lefarmico.donetime.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.data.repository.LibraryRepositoryImpl
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.donetime.App
import com.lefarmico.donetime.intents.ExerciseListIntent
import com.lefarmico.donetime.views.base.BaseViewModel
import javax.inject.Inject

class ExerciseListViewModel : BaseViewModel<ExerciseListIntent>() {

    @Inject
    lateinit var repo: LibraryRepositoryImpl

    val exercisesLiveData = MutableLiveData<DataState<List<LibraryDto.Exercise>>>()

    init {
        App.appComponent.inject(this)
    }

    fun getExercises(subCategoryId: Int) {
        repo.getExercises(subCategoryId)
            .subscribe { dataState ->
                when (dataState) {
                    DataState.Empty -> {
                        exercisesLiveData.postValue(dataState)
                    }
                    is DataState.Error -> {}
                    DataState.Loading -> {}
                    is DataState.Success -> {
                        exercisesLiveData.postValue(dataState)
                    }
                }
            }
    }

    fun cleanAll() {
        exercisesLiveData.postValue(DataState.Empty)
    }

    override fun onTriggerEvent(eventType: ExerciseListIntent) {
        when (eventType) {
            is ExerciseListIntent.GetExercises -> getExercises(eventType.subcategoryId)
            ExerciseListIntent.CleanAll -> cleanAll()
        }
    }
}
