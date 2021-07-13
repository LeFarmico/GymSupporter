package com.lefarmico.donetime.viewHolders.factories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lefarmico.donetime.databinding.ItemDateBinding
import com.lefarmico.donetime.databinding.ItemExerciseBinding
import com.lefarmico.donetime.viewHolders.DateViewHolder
import com.lefarmico.donetime.viewHolders.ExerciseViewHolder
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

enum class ExerciseMenuFactory : IViewHolderFactory<ItemType> {
    DATE {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return DateViewHolder(
                ItemDateBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    },
    EXERCISE {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return ExerciseViewHolder(
                ItemExerciseBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }
}
