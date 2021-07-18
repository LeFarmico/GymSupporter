package com.lefarmico.donetime.adapters.viewHolders

import com.lefarmico.donetime.adapters.exercise.entity.ExerciseName
import com.lefarmico.donetime.databinding.ItemExerciseTitleBinding
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class ExerciseViewHolder(
    private val itemExerciseTitleBinding: ItemExerciseTitleBinding
) : LeRecyclerViewHolder<ItemType>(itemExerciseTitleBinding.root) {
    val exerciseName = itemExerciseTitleBinding.exerciseName
    val tags = itemExerciseTitleBinding.tags

    override fun bind(item: ItemType, position: Int, itemCount: Int) {
        val exercise = (item as ExerciseName)
        exerciseName.text = exercise.name
        tags.text = exercise.tags
    }
}