package com.lefarmico.donetime.adapters.viewHolders.factories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lefarmico.donetime.adapters.viewHolders.curWorkout.ExerciseAddDelButtonViewHolder
import com.lefarmico.donetime.adapters.viewHolders.exerciseList.ExerciseSetViewHolder
import com.lefarmico.donetime.adapters.viewHolders.exerciseList.ExerciseTitleViewHolder
import com.lefarmico.donetime.databinding.ItemAddButtonBinding
import com.lefarmico.donetime.databinding.ItemExerciseSetBinding
import com.lefarmico.donetime.databinding.ItemExerciseTitleBinding
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

enum class ExerciseViewHolderFactory : IViewHolderFactory<ItemType> {
    SET {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return ExerciseSetViewHolder(
                ItemExerciseSetBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    },
    EXERCISE_TITLE {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return ExerciseTitleViewHolder(
                ItemExerciseTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    },
    BUTTONS {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return ExerciseAddDelButtonViewHolder(
                ItemAddButtonBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }
}
