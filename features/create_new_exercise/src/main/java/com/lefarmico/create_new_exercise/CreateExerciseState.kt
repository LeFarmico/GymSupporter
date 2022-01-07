package com.lefarmico.create_new_exercise

import com.lefarmico.core.base.BaseState
import java.lang.Exception

sealed class CreateExerciseState : BaseState.State {
    object Loading : CreateExerciseState()
    data class ExceptionResult(val exception: Exception) : CreateExerciseState()
}
