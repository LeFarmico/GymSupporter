package com.lefarmico.donetime.ui.workout.adapters.workout

import android.view.ViewGroup
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

enum class WorkoutFactory : IViewHolderFactory<ItemType> {
    EXERCISE {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            TODO("Not yet implemented")
        }
    },
    ADD_EXERCISE {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            TODO("Not yet implemented")
        }
    }
}
