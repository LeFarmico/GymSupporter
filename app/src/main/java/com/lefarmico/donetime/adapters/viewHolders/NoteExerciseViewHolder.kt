package com.lefarmico.donetime.adapters.viewHolders

import com.lefarmico.donetime.data.entities.notes.SetNote
import com.lefarmico.donetime.databinding.ItemNoteSetBinding
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class NoteExerciseViewHolder(
    itemNoteSetBinding: ItemNoteSetBinding
) : LeRecyclerViewHolder<ItemType>(itemNoteSetBinding.root) {

    private val setNumber = itemNoteSetBinding.setNumber
    private val weight = itemNoteSetBinding.weight
    private val reps = itemNoteSetBinding.reps
    
    override fun bind(item: ItemType, position: Int, itemCount: Int) {
        item as SetNote
        setNumber.text = "${item.setNumber}. "
        weight.text = "Weight: ${item.weight} "
        reps.text = "Reps: ${item.reps}"
    }
}
