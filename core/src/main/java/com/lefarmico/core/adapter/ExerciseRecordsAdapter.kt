package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.R
import com.lefarmico.core.databinding.ItemNoteExerciseBinding
import com.lefarmico.core.entity.WorkoutRecordsViewData

class ExerciseRecordsAdapter : RecyclerView.Adapter<ExerciseRecordsAdapter.WorkoutNoteViewHolder>() {

    var items = listOf<WorkoutRecordsViewData.ExerciseWithSets>()
        set(value) {
            field = value
            notifyItemRangeChanged(0, items.size)
        }

    class WorkoutNoteViewHolder(
        itemNoteExerciseBinding: ItemNoteExerciseBinding
    ) : RecyclerView.ViewHolder(itemNoteExerciseBinding.root) {

        private val context = itemNoteExerciseBinding.root.context
        private val exerciseField = itemNoteExerciseBinding.exercise
        private val setList = itemNoteExerciseBinding.setsList

        fun bind(exercise: WorkoutRecordsViewData.Exercise, number: Int) {
            exerciseField.text = context.getString(R.string.exercise_field, number.toString(), exercise.exerciseName)
        }
        fun bindAdapter(adapter: SetRecordsAdapter) {
            setList.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutNoteViewHolder {
        return WorkoutNoteViewHolder(
            ItemNoteExerciseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WorkoutNoteViewHolder, position: Int) {
        val exerciseSetList = items[position].setList
        val setAdapter = SetRecordsAdapter().apply {
            items = exerciseSetList
        }
        holder.bind(items[position].exercise, position + 1)
        holder.bindAdapter(setAdapter)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
