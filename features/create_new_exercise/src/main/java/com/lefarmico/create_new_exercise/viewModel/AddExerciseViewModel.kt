package com.lefarmico.create_new_exercise.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.create_new_exercise.intent.AddExerciseIntent
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import javax.inject.Inject

class AddExerciseViewModel @Inject constructor() : BaseViewModel<AddExerciseIntent>() {

    @Inject lateinit var repo: LibraryRepository

    val addExerciseLiveData = MutableLiveData<DataState<Long>>()

    private fun addNewExercise(title: String, description: String, imageRes: String, subcategoryId: Int) {
        val exercise = LibraryDto.Exercise(
            title = title,
            description = description,
            imageRes = imageRes,
            subCategoryId = subcategoryId
        )
        repo.addExercise(exercise)
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        addExerciseLiveData.postValue(DataState.Success(1))
                    }
                    else -> {
                        addExerciseLiveData.postValue(dataState)
                    }
                }
            }
    }

    private fun setDefaultState() {
        addExerciseLiveData.postValue(DataState.Empty)
    }

    override fun onTriggerEvent(eventType: com.lefarmico.create_new_exercise.intent.AddExerciseIntent) {
        when (eventType) {
            is com.lefarmico.create_new_exercise.intent.AddExerciseIntent.AddExerciseResult -> {
                addNewExercise(
                    eventType.title,
                    eventType.description,
                    eventType.imageRes,
                    eventType.subcategoryId
                )
            }
            com.lefarmico.create_new_exercise.intent.AddExerciseIntent.DefaultState -> setDefaultState()
        }
    }
}
