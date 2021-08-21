package com.lefarmico.donetime.data.entities.exercise

import com.lefarmico.donetime.data.models.ICurrentExerciseSetItem

interface IExerciseEntity {
    val name: String
    val tag: String
    val sets: List<ICurrentExerciseSetItem>
}
