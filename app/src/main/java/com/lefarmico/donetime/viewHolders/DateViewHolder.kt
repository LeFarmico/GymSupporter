package com.lefarmico.donetime.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.lefarmico.donetime.databinding.ItemDateBinding
import com.lefarmico.donetime.viewHolders.entity.ExerciseDate
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class DateViewHolder(
    dateItemBinding: ItemDateBinding
) : LeRecyclerViewHolder<ItemType>(dateItemBinding.root) {
    private val date: TextView = dateItemBinding.dateTextView

    override fun bind(item: ItemType) {
        val exerciseDate = (item as ExerciseDate)
        date.text = exerciseDate.date
    }

    override fun initialise(parent: ViewGroup): LeRecyclerViewHolder<ItemType> {
        return DateViewHolder(
            ItemDateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}
