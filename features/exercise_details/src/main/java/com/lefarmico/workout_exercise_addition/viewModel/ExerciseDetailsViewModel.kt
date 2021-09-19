package com.lefarmico.workout_exercise_addition.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.workout_exercise_addition.intent.ExerciseDetailsIntent
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
