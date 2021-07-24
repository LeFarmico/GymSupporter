package com.lefarmico.donetime.ui.workout.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.donetime.databinding.ItemExerciseFullBinding

class ExerciseViewHolder(
    itemExerciseFullBinding: ItemExerciseFullBinding
) : RecyclerView.ViewHolder(
    itemExerciseFullBinding.root
) {
    val recycler = itemExerciseFullBinding.exerciseItem
}
