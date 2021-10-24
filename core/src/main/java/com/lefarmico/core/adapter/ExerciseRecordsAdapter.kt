package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.databinding.ItemNoteExerciseBinding
import com.lefarmico.core.entity.WorkoutRecordsViewData

class ExerciseRecordsAdapter : RecyclerView.Adapter<ExerciseRecordsAdapter.WorkoutNoteViewHolder>() {

    var items = listOf<WorkoutRecordsViewData.ExerciseWithSets>()

    class WorkoutNoteViewHolder(
        itemNoteExerciseBinding: ItemNoteExerciseBinding
    ) : RecyclerView.ViewHolder(itemNoteExerciseBinding.root) {

        private var exercise = itemNoteExerciseBinding.exerciseName
        private var exerciseNumber = itemNoteExerciseBinding.exerciseNumber
        private val setList = itemNoteExerciseBinding.setsList

        fun bind(exercise: WorkoutRecordsViewData.Exercise) {
            this.exercise.text = exercise.exerciseName
        }
        fun bindAdapter(adapter: SetRecordsAdapter) {
            setList.adapter = adapter
        }
        fun setExerciseNumber(number: Int) {
            exerciseNumber.text = number.toString()
        }
    }

    fun setExercise(
        exerciseWithSetsList: List<WorkoutRecordsViewData.ExerciseWithSets>
    ) {
//        val oldList = items
        items = exerciseWithSetsList
        notifyDataSetChanged()
//        val diffCallback = ExerciseRecordsDiffCallback(oldList, items)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        diffResult.dispatchUpdatesTo(this)
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
        holder.bind(items[position].exercise)
        holder.bindAdapter(setAdapter)
        holder.setExerciseNumber(position + 1)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
