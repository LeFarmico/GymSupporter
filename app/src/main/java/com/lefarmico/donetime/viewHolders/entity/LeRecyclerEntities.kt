package com.lefarmico.donetime.viewHolders.entity

import com.lefarmico.donetime.viewHolders.factories.ExerciseMenuFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

data class Exercise(
    val name: String,
    val description: String,
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = ExerciseMenuFactory.EXERCISE
}

data class ExerciseDate(
    val date: String
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = ExerciseMenuFactory.DATE
}
