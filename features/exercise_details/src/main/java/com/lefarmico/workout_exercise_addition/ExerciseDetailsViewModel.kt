package com.lefarmico.workout_exercise_addition

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.LibraryRepository
import javax.inject.Inject

class ExerciseDetailsViewModel @Inject constructor(
    private val repo: LibraryRepository
) : BaseViewModel<
    ExerciseDetailsIntent, ExerciseDetailsState, ExerciseDetailsEvent
    >() {

    private fun getExerciseFromDB(exerciseId: Int) {
        repo.getExercise(exerciseId)
            .observeUi()
            .doAfterSuccess { dataState -> mState.value = dataState.reduce() }
            .subscribe()
    }

    override fun triggerIntent(intent: ExerciseDetailsIntent) {
        return when (intent) {
            is ExerciseDetailsIntent.GetExercise -> getExerciseFromDB(intent.exerciseId)
        }
    }
}
