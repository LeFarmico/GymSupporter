package com.lefarmico.exercise_menu.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.intent.ExerciseListIntent
import javax.inject.Inject

class ExerciseListViewModel @Inject constructor() : BaseViewModel<ExerciseListIntent>() {

    @Inject
    lateinit var repo: LibraryRepository

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
            is ExerciseListIntent.AddExerciseToWorkoutScreen -> TODO()
            is ExerciseListIntent.GoToExerciseDetailsScreen -> TODO()
        }
    }
}
