package com.lefarmico.donetime.adapters.delegates.exerciseDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.donetime.adapters.CurrentSetAdapter
import com.lefarmico.donetime.databinding.ItemExerciseBinding

class CurrentExerciseDelegate() : AbsListItemAdapterDelegate<
    WorkoutRecordsDto.Exercise,
    WorkoutRecordsDto,
    CurrentExerciseDelegate.ExerciseViewHolder
    >() {

    class ExerciseViewHolder(
        itemExerciseBinding: ItemExerciseBinding
    ) : RecyclerView.ViewHolder(itemExerciseBinding.root) {

        private val recycler = itemExerciseBinding.exerciseItem
        private val exerciseTitle = itemExerciseBinding.exerciseTitle.exerciseName
        private val exerciseTag = itemExerciseBinding.exerciseTitle.tags

        val plusButton = itemExerciseBinding.buttons.plusButton
        val minusButton = itemExerciseBinding.buttons.minusButton

        fun bindAdapter(adapter: CurrentSetAdapter) {
            recycler.adapter = adapter
        }
        fun bind(exerciseData: WorkoutRecordsDto.Exercise) {
            exerciseTitle.text = exerciseData.exerciseName
            exerciseTag.text = exerciseData.exerciseName
        }
    }

    override fun isForViewType(
        item: WorkoutRecordsDto,
        items: MutableList<WorkoutRecordsDto>,
        position: Int
    ): Boolean {
        return item is WorkoutRecordsDto.Exercise
    }

    override fun onCreateViewHolder(parent: ViewGroup): ExerciseViewHolder {
        return ExerciseViewHolder(
            ItemExerciseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        item: WorkoutRecordsDto.Exercise,
        holder: ExerciseViewHolder,
        payloads: MutableList<Any>
    ) {
        val adapter = CurrentSetAdapter()
        adapter.items = item.noteSetList.toMutableList()
        holder.bindAdapter(adapter)
        holder.bind(item)
    }
}
