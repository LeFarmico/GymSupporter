package com.lefarmico.donetime.adapters.viewHolders

import android.widget.TextView
import androidx.annotation.DrawableRes
import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseMuscleSetEntity
import com.lefarmico.donetime.databinding.ItemExerciseSetBinding
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class ExerciseSetViewHolder(
    private val exerciseItemBinding: ItemExerciseSetBinding
) : LeRecyclerViewHolder<ItemType>(exerciseItemBinding.root) {
    private val setNumber: TextView = exerciseItemBinding.setNumber
    private val weights: TextView = exerciseItemBinding.weights
    private var reps = exerciseItemBinding.reps

    override fun bind(item: ItemType, position: Int, itemCount: Int) {
        val set = (item as ExerciseMuscleSetEntity)
        setNumber.text = "${set.setNumber}. Set"
        weights.text = "${set.weights} Kg"
        reps.text = "${set.reps} Reps"
    }

    fun bindBackGround(@DrawableRes drawable: Int) {
        exerciseItemBinding.root.setBackgroundResource(drawable)
    }
}
