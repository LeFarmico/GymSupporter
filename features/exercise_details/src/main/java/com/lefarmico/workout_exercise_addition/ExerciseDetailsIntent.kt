package com.lefarmico.workout_exercise_addition

import com.lefarmico.core.base.BaseIntent

sealed class ExerciseDetailsIntent : BaseIntent {

    data class GetExercise(val exerciseId: Int) : ExerciseDetailsIntent()
}
