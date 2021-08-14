package com.lefarmico.donetime.adapters.viewHolders

import com.lefarmico.donetime.data.entities.workout.exercise.ExerciseNameEntity
import com.lefarmico.donetime.databinding.ItemExerciseTitleBinding
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class ExerciseTitleViewHolder(
    itemExerciseTitleBinding: ItemExerciseTitleBinding
) : LeRecyclerViewHolder<ItemType>(itemExerciseTitleBinding.root) {
    private val exerciseName = itemExerciseTitleBinding.exerciseName
    private val tags = itemExerciseTitleBinding.tags

    override fun bind(item: ItemType, position: Int, itemCount: Int) {
        val exercise = (item as ExerciseNameEntity)
        exerciseName.text = exercise.name
        tags.text = exercise.tags
    }
}
