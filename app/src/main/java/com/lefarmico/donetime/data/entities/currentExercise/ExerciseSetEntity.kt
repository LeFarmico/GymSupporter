package com.lefarmico.donetime.data.entities.currentExercise

data class ExerciseSetEntity(
    val weights: Float,
    val reps: Int,
) {
    var setNumber: Int = 0
}
