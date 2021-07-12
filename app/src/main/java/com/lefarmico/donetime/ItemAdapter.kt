package com.lefarmico.donetime

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.lefarmico.donetime.databinding.ItemDateBinding
import com.lefarmico.donetime.databinding.ItemExerciseBinding
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

data class Exercise(
    val name: String,
    val description: String,
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = EnumViewTypes.EXERCISE
}

data class ExerciseDate(
    val date: String
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = EnumViewTypes.DATE
}

enum class EnumViewTypes : IViewHolderFactory<ItemType> {
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

class ExerciseViewHolder(
    exerciseItemBinding: ItemExerciseBinding
) : LeRecyclerViewHolder<ItemType>(exerciseItemBinding.root) {
    private val exerciseName: TextView = exerciseItemBinding.exNameTextView
    private val exerciseDescription: TextView = exerciseItemBinding.exDescriptionTextView

    override fun bind(item: ItemType) {
        val exercise = (item as Exercise)
        exerciseName.text = exercise.name
        exerciseDescription.text = exercise.description
    }
}

class DateViewHolder(
    dateItemBinding: ItemDateBinding
) : LeRecyclerViewHolder<ItemType>(dateItemBinding.root) {
    private val date: TextView = dateItemBinding.dateTextView

    override fun bind(item: ItemType) {
        val exerciseDate = (item as ExerciseDate)
        date.text = exerciseDate.date
    }
}
