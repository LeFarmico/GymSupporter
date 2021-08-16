package com.lefarmico.donetime.data.entities.workout.exercise

import com.lefarmico.lerecycle.ItemType

interface IExerciseData : ItemType, IExerciseEntity {
    override val name: String
    override val tag: String
    override var sets: MutableList<ISetEntity>
    var isActive: Boolean
    var addButtonEvent: (() -> ISetEntity)
    var delButtonEvent: ((ExerciseData) -> Unit)
    fun getSetCount(): Int
}
