package com.lefarmico.donetime.adapters.viewHolders.curWorkout

import com.lefarmico.donetime.adapters.ExerciseAdapter
import com.lefarmico.donetime.databinding.ItemExerciseFullBinding
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class ExerciseViewHolder(
    itemExerciseFullBinding: ItemExerciseFullBinding
) : LeRecyclerViewHolder<ItemType>(
    itemExerciseFullBinding.root
) {
    private val recycler = itemExerciseFullBinding.exerciseItem
    override fun bind(item: ItemType, position: Int, itemCount: Int) {}
    fun bindAdapter(exerciseAdapter: ExerciseAdapter) {
        recycler.adapter = exerciseAdapter
    }
}
