package com.lefarmico.donetime.data.entities.traning.exercise

import com.lefarmico.donetime.adapters.viewHolders.ExerciseViewHolderFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

data class ExerciseNameEntity(
    val name: String,
    val tags: String,
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = ExerciseViewHolderFactory.EXERCISE_TITLE
}
