package com.lefarmico.workout_exercise_addition

import com.lefarmico.core.base.BaseAction

sealed class ExerciseDetailsAction : BaseAction {
    data class GetExercise(val exerciseId: Int) : ExerciseDetailsAction()
}
