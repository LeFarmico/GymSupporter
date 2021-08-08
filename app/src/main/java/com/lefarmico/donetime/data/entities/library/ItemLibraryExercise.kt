package com.lefarmico.donetime.data.entities.library

import com.lefarmico.donetime.adapters.ExerciseListViewHolderFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

data class ItemLibraryExercise(
    val title: String,
    val description: String,
    val image: String?
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = ExerciseListViewHolderFactory.ITEM
}
