package com.lefarmico.donetime.adapters.viewHolders

import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.data.entities.library.ItemLibraryExercise
import com.lefarmico.donetime.data.entities.library.ItemLibrarySubCategory
import com.lefarmico.donetime.databinding.ItemExerciseListBinding
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class ExerciseListViewHolder(
    exerciseListViewHolder: ItemExerciseListBinding
) : LeRecyclerViewHolder<ItemType>(exerciseListViewHolder.root) {

    private val exerciseText = exerciseListViewHolder.text

    override fun bind(item: ItemType, position: Int, itemCount: Int) {
        when (item) {
            is ItemLibraryCategory -> {
                exerciseText.text = item.title
            }
            is ItemLibrarySubCategory -> {
                exerciseText.text = item.title
            }
            is ItemLibraryExercise -> {
                exerciseText.text = item.title
                exerciseText.setCompoundDrawables(null, null, null, null)
            }
        }
    }
}
