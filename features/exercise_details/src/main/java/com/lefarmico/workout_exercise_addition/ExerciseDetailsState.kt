package com.lefarmico.workout_exercise_addition

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.entity.LibraryViewData
import java.lang.Exception

sealed class ExerciseDetailsState : BaseState.State {
    data class ExerciseResult(val exercise: LibraryViewData.Exercise) : ExerciseDetailsState()
    object Loading : ExerciseDetailsState()
}
