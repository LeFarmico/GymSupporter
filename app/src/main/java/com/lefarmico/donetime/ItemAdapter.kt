package com.lefarmico.donetime

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.lefarmico.donetime.databinding.TestItem2Binding
import com.lefarmico.donetime.databinding.TestItemBinding
import com.lefarmico.lerecycle.LeRecyclerAdapter
import com.lefarmico.lerecycle.LeRecyclerViewHolder
import java.lang.IllegalArgumentException

class ItemAdapter : LeRecyclerAdapter<ItemType, LeRecyclerViewHolder<ItemType>>() {

    override var items: MutableList<ItemType> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override var onClickEvent: OnClickEvent<ItemType>? = null

    class DateViewHolder(
        dateItemBinding: TestItemBinding
    ) : LeRecyclerViewHolder<ItemType>(dateItemBinding.root) {
        private val date: TextView = dateItemBinding.dateTextView

        override fun bind(item: ItemType) {
            val exerciseDate = (item as ExerciseDate)
            date.text = exerciseDate.date
        }
    }

    class ExerciseViewHolder(
        exerciseItemBinding: TestItem2Binding
    ) : LeRecyclerViewHolder<ItemType>(exerciseItemBinding.root) {
        private val exerciseName: TextView = exerciseItemBinding.exNameTextView
        private val exerciseDescription: TextView = exerciseItemBinding.exDescriptionTextView

        override fun bind(item: ItemType) {
            val exercise = (item as Exercise)
            exerciseName.text = exercise.name
            exerciseDescription.text = exercise.description
        }
    }

    override fun onCreateViewHolderWithListener(
        parent: ViewGroup,
        viewType: Int
    ): LeRecyclerViewHolder<ItemType> {
        return when (viewType) {
            ItemViewType.EXERCISE.typeNumber -> {
                ExerciseViewHolder(
                    TestItem2Binding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            ItemViewType.DATE.typeNumber -> {
                DateViewHolder(
                    TestItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> throw (IllegalArgumentException("Illegal viewType parameter."))
        }
    }

    override fun onBindViewHolder(holder: LeRecyclerViewHolder<ItemType>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type.typeNumber
    }
}
