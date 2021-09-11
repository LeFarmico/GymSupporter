package com.lefarmico.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.presentation.intents.ExerciseDetailsIntent
import com.lefarmico.presentation.views.base.BaseViewModel
import javax.inject.Inject

class ExerciseDetailsViewModel @Inject constructor() : BaseViewModel<ExerciseDetailsIntent>() {

    @Inject lateinit var repo: LibraryRepository

    val libraryExerciseLiveData = MutableLiveData<DataState<LibraryDto.Exercise>>()

    private fun getExerciseFromDB(exerciseId: Int) {
        repo.getExercise(exerciseId).subscribe { dataState ->
            libraryExerciseLiveData.postValue(dataState)
        }
    }

    override fun onTriggerEvent(eventType: ExerciseDetailsIntent) {
        when (eventType) {
            is ExerciseDetailsIntent.GetExercise -> getExerciseFromDB(eventType.exerciseId)
        }
    }
}
