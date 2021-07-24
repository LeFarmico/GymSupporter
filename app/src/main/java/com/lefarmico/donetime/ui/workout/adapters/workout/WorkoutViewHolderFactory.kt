package com.lefarmico.donetime.ui.workout.adapters.workout

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lefarmico.donetime.databinding.ItemAddExerciseBinding
import com.lefarmico.donetime.databinding.ItemExerciseFullBinding
import com.lefarmico.donetime.ui.workout.viewHolders.ExerciseViewHolder
import com.lefarmico.donetime.ui.workout.viewHolders.WorkoutAddExViewHolder
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

enum class WorkoutViewHolderFactory : IViewHolderFactory<ItemType> {
    EXERCISE {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return ExerciseViewHolder(
                ItemExerciseFullBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    },
    ADD_EXERCISE {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return WorkoutAddExViewHolder(
                ItemAddExerciseBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }
}
