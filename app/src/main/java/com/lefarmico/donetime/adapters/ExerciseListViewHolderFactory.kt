package com.lefarmico.donetime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lefarmico.donetime.adapters.viewHolders.ExerciseListViewHolder
import com.lefarmico.donetime.databinding.ItemExerciseListBinding
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

enum class ExerciseListViewHolderFactory : IViewHolderFactory<ItemType> {
    MENU_ITEM {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return ExerciseListViewHolder(
                ItemExerciseListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    },
    ITEM {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return ExerciseListViewHolder(
                ItemExerciseListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }
}
