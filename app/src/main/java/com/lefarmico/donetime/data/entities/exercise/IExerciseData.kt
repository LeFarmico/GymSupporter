package com.lefarmico.donetime.data.entities.exercise

import com.lefarmico.donetime.data.models.ICurrentExerciseItem
import com.lefarmico.donetime.data.models.ICurrentExerciseSetItem

interface IExerciseData : IExerciseEntity {
    override val name: String
    override val tag: String
    override var sets: MutableList<ICurrentExerciseSetItem>
    var isActive: Boolean
    var addButtonEvent: (() -> ICurrentExerciseSetItem)
    var delButtonEvent: ((ExerciseData) -> Unit)
    fun getSetCount(): Int
    fun getItems(): MutableList<ICurrentExerciseItem>
}
