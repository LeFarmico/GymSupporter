package com.lefarmico.presentation.adapters.delegates.exerciseDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.domain.repository.BaseRepository
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.donetime.adapters.CurrentSetAdapter

class CurrentExerciseDelegate() : AbsListItemAdapterDelegate<
    CurrentWorkoutRepository,
    BaseRepository,
        CurrentExerciseDelegate.ExerciseViewHolder
    >() {

    class ExerciseViewHolder(
        itemExerciseBinding: ItemExerciseBinding
    ) : RecyclerView.ViewHolder(itemExerciseBinding.root) {

        private val recycler = itemExerciseBinding.exerciseItem
        private val exerciseTitle = itemExerciseBinding.exerciseTitle.exerciseName
        private val exerciseTag = itemExerciseBinding.exerciseTitle.tags

        fun bindAdapter(adapter: CurrentSetAdapter) {
            recycler.adapter = adapter
        }
        fun bind(exerciseData: ExerciseData) {
            exerciseTitle.text = exerciseData.name
            exerciseTag.text = exerciseData.tag
        }
    }

    override fun isForViewType(
        item: ICurrentExerciseItem,
        items: MutableList<ICurrentExerciseItem>,
        position: Int
    ): Boolean {
        return item is ExerciseData
    }

    override fun onCreateViewHolder(parent: ViewGroup): ExerciseViewHolder {
        return ExerciseViewHolder(
            ItemExerciseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        item: ExerciseData,
        holder: ExerciseViewHolder,
        payloads: MutableList<Any>
    ) {
        val adapter = CurrentSetAdapter()
        adapter.items = item.exerciseSetList.setList.toMutableList()
        holder.bindAdapter(adapter)
        holder.bind(item)
    }
}
