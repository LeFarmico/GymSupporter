package com.lefarmico.donetime.adapters.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lefarmico.donetime.databinding.ItemAddButtonBinding
import com.lefarmico.donetime.databinding.ItemExerciseSetBinding
import com.lefarmico.donetime.databinding.ItemExerciseTitleBinding
import com.lefarmico.donetime.viewHolders.AddDelButtonViewHolder
import com.lefarmico.donetime.viewHolders.ExerciseSetViewHolder
import com.lefarmico.donetime.viewHolders.ExerciseViewHolder
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

enum class ExerciseMenuFactory : IViewHolderFactory<ItemType> {
    SET {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return ExerciseSetViewHolder(
                ItemExerciseSetBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    },
    EXERCISE {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return ExerciseViewHolder(
                ItemExerciseTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    },
    BUTTONS {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return AddDelButtonViewHolder(
                ItemAddButtonBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }
}
