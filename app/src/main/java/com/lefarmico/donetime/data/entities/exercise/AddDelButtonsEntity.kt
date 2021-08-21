package com.lefarmico.donetime.data.entities.exercise

import com.lefarmico.donetime.data.models.ICurrentExerciseItem

data class AddDelButtonsEntity(
    val addButtonCallback: () -> Unit,
    val deleteButtonCallback: () -> Unit,
) : ICurrentExerciseItem
