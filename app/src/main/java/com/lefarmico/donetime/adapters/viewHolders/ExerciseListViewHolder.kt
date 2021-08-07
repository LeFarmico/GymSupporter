package com.lefarmico.donetime.adapters.viewHolders

import com.lefarmico.donetime.data.entities.library.LibraryExercise
import com.lefarmico.donetime.data.entities.library.LibraryExerciseCategory
import com.lefarmico.donetime.data.entities.library.LibraryExerciseSubCategory
import com.lefarmico.donetime.databinding.ItemExerciseListBinding
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class ExerciseListViewHolder(
    exerciseListViewHolder: ItemExerciseListBinding
) : LeRecyclerViewHolder<ItemType>(exerciseListViewHolder.root) {

    private val exerciseText = exerciseListViewHolder.text

    override fun bind(item: ItemType, position: Int, itemCount: Int) {
        when (item) {
            is LibraryExerciseCategory -> {
                exerciseText.text = item.title
            }
            is LibraryExerciseSubCategory -> {
                exerciseText.text = item.title
            }
            is LibraryExercise -> {
                exerciseText.text = item.title
                exerciseText.setCompoundDrawables(null, null, null, null)
            }
        }
    }
}
