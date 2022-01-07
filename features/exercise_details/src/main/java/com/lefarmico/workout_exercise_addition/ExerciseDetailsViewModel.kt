package com.lefarmico.workout_exercise_addition

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.LibraryRepository
import javax.inject.Inject

class ExerciseDetailsViewModel @Inject constructor() : BaseViewModel<
    ExerciseDetailsIntent, ExerciseDetailsAction, ExerciseDetailsState, ExerciseDetailsEvent
    >() {

    @Inject lateinit var repo: LibraryRepository

    private fun getExerciseFromDB(exerciseId: Int) {
        repo.getExercise(exerciseId)
            .observeUi()
            .doAfterSuccess { dataState -> dataState.reduce() }
            .subscribe()
    }

    override fun triggerAction(action: ExerciseDetailsAction) {
        when (action) {
            is ExerciseDetailsAction.GetExercise -> getExerciseFromDB(action.exerciseId)
        }
    }

    override fun intentToAction(intent: ExerciseDetailsIntent): ExerciseDetailsAction {
        return when (intent) {
            is ExerciseDetailsIntent.GetExercise -> ExerciseDetailsAction.GetExercise(intent.exerciseId)
        }
    }
}
