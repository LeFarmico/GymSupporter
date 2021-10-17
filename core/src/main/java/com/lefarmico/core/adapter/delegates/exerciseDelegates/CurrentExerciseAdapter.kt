package com.lefarmico.core.adapter.delegates.exerciseDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.adapter.CurrentSetAdapter
import com.lefarmico.core.databinding.ItemExerciseBinding
import com.lefarmico.core.entity.WorkoutRecordsViewData

class CurrentExerciseAdapter() : RecyclerView.Adapter<
    CurrentExerciseAdapter.ExerciseViewHolder
    >() {

    lateinit var plusButtonCallback: (Int) -> Unit
    var items = listOf<WorkoutRecordsViewData.ExerciseWithSets>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    class ExerciseViewHolder(
        itemExerciseBinding: ItemExerciseBinding
    ) : RecyclerView.ViewHolder(itemExerciseBinding.root) {

        private val recycler = itemExerciseBinding.exerciseItem
        private val exerciseTitle = itemExerciseBinding.exerciseTitle.exerciseName
        private val exerciseTag = itemExerciseBinding.exerciseTitle.tags

        val rootLayout = itemExerciseBinding.exerciseTitle.root

        val plusButton = itemExerciseBinding.buttons.plusButton
        val minusButton = itemExerciseBinding.buttons.minusButton

        private val decorator = DividerItemDecoration(recycler.context, DividerItemDecoration.VERTICAL)

        fun bindAdapter(adapter: CurrentSetAdapter) {
            recycler.adapter = adapter
            recycler.addItemDecoration(decorator)
        }
        fun bind(title: String, tag: String) {
            exerciseTitle.text = title
            exerciseTag.text = tag
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ItemExerciseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val title = items[position].exercise.exerciseName
        val adapter = CurrentSetAdapter()
        plusButtonCallback(items[position].exercise.id)
        adapter.items = items[position].setList.toMutableList()
        holder.bindAdapter(adapter)
        holder.bind(title, title)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
