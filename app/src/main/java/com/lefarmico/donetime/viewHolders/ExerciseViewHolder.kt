package com.lefarmico.donetime.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.lefarmico.donetime.databinding.ItemExerciseBinding
import com.lefarmico.donetime.viewHolders.entity.Exercise
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class ExerciseViewHolder(
    exerciseItemBinding: ItemExerciseBinding
) : LeRecyclerViewHolder<ItemType>(exerciseItemBinding.root) {
    private val exerciseName: TextView = exerciseItemBinding.exNameTextView
    private val exerciseDescription: TextView = exerciseItemBinding.exDescriptionTextView

    override fun bind(item: ItemType) {
        val exercise = (item as Exercise)
        exerciseName.text = exercise.name
        exerciseDescription.text = exercise.description
    }

    override fun initialise(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
        return ExerciseViewHolder(
            ItemExerciseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}
