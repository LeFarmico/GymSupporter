package com.lefarmico.donetime.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.data.repository.LibraryRepositoryImpl
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.donetime.App
import com.lefarmico.donetime.intents.ExerciseDetailsIntent
import com.lefarmico.donetime.views.base.BaseViewModel
import javax.inject.Inject

class ExerciseDetailsViewModel : BaseViewModel<ExerciseDetailsIntent>() {

    @Inject lateinit var repo: LibraryRepositoryImpl

    val libraryExerciseLiveData = MutableLiveData<DataState<LibraryDto.Exercise>>()

    init {
        App.appComponent.inject(this)
    }

    fun getExerciseFromDB(exerciseId: Int) {
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
