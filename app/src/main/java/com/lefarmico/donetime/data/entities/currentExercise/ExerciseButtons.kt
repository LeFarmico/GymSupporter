package com.lefarmico.donetime.data.entities.currentExercise

data class ExerciseButtons(
    val addButtonCallback: () -> Unit,
    val deleteButtonCallback: () -> Unit,
) : ICurrentExerciseItem
