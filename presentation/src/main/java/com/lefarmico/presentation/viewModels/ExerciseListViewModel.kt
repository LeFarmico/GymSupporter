package com.lefarmico.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.data.repository.LibraryRepositoryImpl
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.presentation.intents.ExerciseListIntent
import com.lefarmico.presentation.views.base.BaseViewModel
import javax.inject.Inject

class ExerciseListViewModel @Inject constructor() : BaseViewModel<ExerciseListIntent>() {

    @Inject
    lateinit var repo: LibraryRepositoryImpl

    val exercisesLiveData = MutableLiveData<DataState<List<LibraryDto.Exercise>>>()

    private fun getExercises(subCategoryId: Int) {
        repo.getExercises(subCategoryId)
            .subscribe { dataState ->
                exercisesLiveData.postValue(dataState)
            }
    }

    private fun cleanAll() {
        exercisesLiveData.postValue(DataState.Empty)
    }

    override fun onTriggerEvent(eventType: ExerciseListIntent) {
        when (eventType) {
            is ExerciseListIntent.GetExercises -> getExercises(eventType.subcategoryId)
            ExerciseListIntent.CleanAll -> cleanAll()
        }
    }
}
