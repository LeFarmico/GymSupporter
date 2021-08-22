package com.lefarmico.donetime.adapters.delegates.exerciseDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.donetime.adapters.CurrentSetAdapter
import com.lefarmico.donetime.data.entities.currentExercise.ExerciseData
import com.lefarmico.donetime.data.entities.currentExercise.ICurrentExerciseItem
import com.lefarmico.donetime.databinding.ItemExerciseBinding

class CurrentExerciseDelegate() : AbsListItemAdapterDelegate<
    ExerciseData,
    ICurrentExerciseItem,
    CurrentExerciseDelegate.ExerciseViewHolder
    >() {

    class ExerciseViewHolder(
        itemExerciseBinding: ItemExerciseBinding
    ) : RecyclerView.ViewHolder(itemExerciseBinding.root) {

        val recycler = itemExerciseBinding.exerciseItem
        val exerciseTitle = itemExerciseBinding.exerciseTitle.exerciseName
        val exerciseTag = itemExerciseBinding.exerciseTitle.tags

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
    }
}
