package com.lefarmico.donetime.data.entities.library

import com.lefarmico.donetime.adapters.ExerciseListViewHolderFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

data class LibraryExercise(
    val title: String,
    val description: String,
    val image: String?,
    val mainGroup: LibraryExerciseSubCategory,
    val supportGroups: List<LibraryExerciseSubCategory>,
    val exTypeTags: LibraryExerciseCategory
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = ExerciseListViewHolderFactory.ITEM
}
