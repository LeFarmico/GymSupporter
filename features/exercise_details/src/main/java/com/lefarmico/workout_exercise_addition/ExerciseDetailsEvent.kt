package com.lefarmico.workout_exercise_addition

import com.lefarmico.core.base.BaseState
import java.lang.Exception

sealed class ExerciseDetailsEvent : BaseState.Event {
    data class ExceptionResult(val exception: Exception) : ExerciseDetailsEvent()
}
