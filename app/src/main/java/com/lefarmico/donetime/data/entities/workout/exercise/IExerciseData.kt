package com.lefarmico.donetime.data.entities.workout.exercise

import com.lefarmico.lerecycle.ItemType

interface IExerciseData : ItemType {
    var name: String
    var tag: String
    var isActive: Boolean
    var addButtonEvent: (() -> ISetEntity)
    var delButtonEvent: ((ExerciseData) -> Unit)
    var sets: MutableList<ISetEntity>
    fun getSetCount(): Int
}
