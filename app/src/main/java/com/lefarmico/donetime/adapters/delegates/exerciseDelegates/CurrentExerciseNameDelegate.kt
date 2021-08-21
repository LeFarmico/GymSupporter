package com.lefarmico.donetime.adapters.delegates.exerciseDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.donetime.data.entities.exercise.ExerciseNameEntity
import com.lefarmico.donetime.data.models.ICurrentExerciseItem
import com.lefarmico.donetime.databinding.ItemExerciseTitleBinding

class CurrentExerciseNameDelegate : AbsListItemAdapterDelegate<
    ExerciseNameEntity,
    ICurrentExerciseItem,
    CurrentExerciseNameDelegate.NameViewHolder
    >() {
            
    class NameViewHolder(
        itemExerciseTitleBinding: ItemExerciseTitleBinding
    ) : RecyclerView.ViewHolder(itemExerciseTitleBinding.root) {

        private val exerciseName = itemExerciseTitleBinding.exerciseName
        private val tags = itemExerciseTitleBinding.tags

        fun bind(item: ExerciseNameEntity) {
            exerciseName.text = item.name
            tags.text = item.tags
        }
    }

    override fun isForViewType(
        item: ICurrentExerciseItem,
        items: MutableList<ICurrentExerciseItem>,
        position: Int
    ): Boolean {
        return item is ExerciseNameEntity
    }

    override fun onCreateViewHolder(parent: ViewGroup): NameViewHolder {
        return NameViewHolder(
            ItemExerciseTitleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        item: ExerciseNameEntity,
        holder: NameViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
