package com.lefarmico.donetime.adapters.viewHolders.factories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lefarmico.donetime.adapters.viewHolders.NoteExerciseViewHolder
import com.lefarmico.donetime.databinding.ItemNoteSetBinding
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

enum class NoteExerciseViewHolderFactory : IViewHolderFactory<ItemType> {
    SET {
        override fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
            return NoteExerciseViewHolder(
                ItemNoteSetBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
