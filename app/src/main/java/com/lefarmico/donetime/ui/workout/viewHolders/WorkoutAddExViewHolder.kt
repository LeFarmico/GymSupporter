package com.lefarmico.donetime.ui.workout.viewHolders

import com.lefarmico.donetime.databinding.ItemAddExerciseBinding
import com.lefarmico.donetime.ui.workout.data.AddExercise
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class WorkoutAddExViewHolder(
    itemAddExerciseBinding: ItemAddExerciseBinding
) : LeRecyclerViewHolder<ItemType>(
    itemAddExerciseBinding.root
) {

    private val button = itemAddExerciseBinding.addExButton

    override fun bind(item: ItemType, position: Int, itemCount: Int) {
        item as AddExercise
        button.setOnClickListener {
            item.addButtonCallback()
        }
    }
}
