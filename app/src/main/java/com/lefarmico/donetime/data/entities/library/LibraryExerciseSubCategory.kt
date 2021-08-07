package com.lefarmico.donetime.data.entities.library

import com.lefarmico.donetime.adapters.ExerciseListViewHolderFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

data class LibraryExerciseSubCategory(
    val title: String,
    val exerciseType: LibraryExerciseCategory
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = ExerciseListViewHolderFactory.MENU_ITEM
}
