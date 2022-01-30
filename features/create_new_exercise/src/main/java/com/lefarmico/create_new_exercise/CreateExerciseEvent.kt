package com.lefarmico.create_new_exercise

import com.lefarmico.core.base.BaseState
import java.lang.Exception

sealed class CreateExerciseEvent : BaseState.Event {

    object ValidationEmpty : CreateExerciseEvent()
    object ValidationAlreadyExist : CreateExerciseEvent()
    object ValidationSuccess : CreateExerciseEvent()

    data class HardException(val exception: Exception) : CreateExerciseEvent()

    sealed class ExerciseActionResult : CreateExerciseEvent() {
        object Success : ExerciseActionResult()
        object Failure : ExerciseActionResult()
    }
}
