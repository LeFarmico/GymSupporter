package com.lefarmico.donetime.data.entities.currentExercise

import com.lefarmico.donetime.data.models.ICurrentExerciseItem

interface IExerciseModel : IExerciseEntity {
    override val name: String
    override val tag: String
    override var exerciseSetList: ExerciseSetList
    var isActive: Boolean
    var addButtonEvent: ((ExerciseData) -> Unit)
    var delButtonEvent: ((ExerciseData) -> Unit)
    fun getSetCount(): Int
    fun getItems(): MutableList<ICurrentExerciseItem>
}
