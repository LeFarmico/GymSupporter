package com.lefarmico.donetime.adapters.viewHolders

import com.lefarmico.donetime.data.entities.workout.AddExerciseEntity
import com.lefarmico.donetime.databinding.ItemAddExerciseBinding
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class WorkoutAddExViewHolder(
    itemAddExerciseBinding: ItemAddExerciseBinding
) : LeRecyclerViewHolder<ItemType>(
    itemAddExerciseBinding.root
) {

    private val button = itemAddExerciseBinding.addExButton

    override fun bind(item: ItemType, position: Int, itemCount: Int) {
        item as AddExerciseEntity
        button.setOnClickListener {
            item.addButtonCallback()
        }
    }
}
