package com.lefarmico.donetime.adapters.viewHolders.homeWorkoutNote

import com.lefarmico.donetime.databinding.ItemWorkoutNoteBinding
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class NoteWorkoutViewHolder(
    itemWorkoutNoteBinding: ItemWorkoutNoteBinding
) : LeRecyclerViewHolder<ItemType>(itemWorkoutNoteBinding.root) {

    val date = itemWorkoutNoteBinding.workoutDate.text
    val sets = itemWorkoutNoteBinding.exercisesRecycler
    override fun bind(item: ItemType, position: Int, itemCount: Int) {
        TODO("Not yet implemented")
    }
}
