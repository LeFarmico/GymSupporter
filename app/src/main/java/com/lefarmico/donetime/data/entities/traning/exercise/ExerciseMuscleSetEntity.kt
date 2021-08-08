package com.lefarmico.donetime.data.entities.traning.exercise

import com.lefarmico.donetime.adapters.viewHolders.factories.ExerciseViewHolderFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

data class ExerciseMuscleSetEntity(
    val weights: Float,
    val reps: Int,
) : ISetEntity {
    override var setNumber: Int = 0
    override val type: IViewHolderFactory<ItemType> = ExerciseViewHolderFactory.SET
}
