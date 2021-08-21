package com.lefarmico.donetime.data.entities.exercise

import com.lefarmico.donetime.data.models.ICurrentExerciseSetItem

data class ExerciseMuscleSetEntity(
    override val weights: Float,
    override val reps: Int,
) : ICurrentExerciseSetItem {
    override var setNumber: Int = 0
}
