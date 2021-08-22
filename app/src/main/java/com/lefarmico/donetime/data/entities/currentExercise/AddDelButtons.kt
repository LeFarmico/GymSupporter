package com.lefarmico.donetime.data.entities.currentExercise

import com.lefarmico.donetime.data.models.ICurrentExerciseItem

data class AddDelButtons(
    val addButtonCallback: () -> Unit,
    val deleteButtonCallback: () -> Unit,
) : ICurrentExerciseItem
