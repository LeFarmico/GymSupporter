package com.lefarmico.donetime.data.entities.library

import com.lefarmico.donetime.adapters.ExerciseListViewHolderFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

data class ItemLibraryCategory(
    val title: String,
    val id: Int
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = ExerciseListViewHolderFactory.CATEGORY
}
