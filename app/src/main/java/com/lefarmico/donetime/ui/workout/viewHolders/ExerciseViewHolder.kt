package com.lefarmico.donetime.ui.workout.viewHolders

import com.lefarmico.donetime.databinding.ItemExerciseFullBinding
import com.lefarmico.donetime.ui.workout.adapters.exercise.ExerciseAdapter
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class ExerciseViewHolder(
    itemExerciseFullBinding: ItemExerciseFullBinding
) : LeRecyclerViewHolder<ItemType>(
    itemExerciseFullBinding.root
) {
    val recycler = itemExerciseFullBinding.exerciseItem
    override fun bind(item: ItemType, position: Int, itemCount: Int) {}
    fun bindAdapter(exerciseAdapter: ExerciseAdapter) {
        recycler.adapter = exerciseAdapter
    }
}
