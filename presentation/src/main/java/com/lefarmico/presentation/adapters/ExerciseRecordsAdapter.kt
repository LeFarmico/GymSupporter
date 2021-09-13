package com.lefarmico.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.presentation.adapters.diffUtil.ExerciseRecordsDiffCallback
import com.lefarmico.presentation.databinding.ItemNoteExerciseBinding

class ExerciseRecordsAdapter : RecyclerView.Adapter<ExerciseRecordsAdapter.WorkoutNoteViewHolder>() {

    var items = mutableListOf<WorkoutRecordsDto.Exercise>()
    
    class WorkoutNoteViewHolder(
        itemNoteExerciseBinding: ItemNoteExerciseBinding
    ) : RecyclerView.ViewHolder(itemNoteExerciseBinding.root) {

        private var exercise = itemNoteExerciseBinding.exerciseName
        private var exerciseNumber = itemNoteExerciseBinding.exerciseNumber
        private val setList = itemNoteExerciseBinding.setsList

        fun bind(noteExercise: WorkoutRecordsDto.Exercise) {
            exercise.text = noteExercise.exerciseName
        }
        fun bindAdapter(adapter: SetRecordsAdapter) {
            setList.adapter = adapter
        }
        fun setExerciseNumber(number: Int) {
            exerciseNumber.text = number.toString()
        }
    }

    fun setExercise(noteExerciseList: List<WorkoutRecordsDto.Exercise>) {
        val oldList = items
        items = noteExerciseList.toMutableList()
        val diffCallback = ExerciseRecordsDiffCallback(oldList, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutNoteViewHolder {
        return WorkoutNoteViewHolder(
            ItemNoteExerciseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WorkoutNoteViewHolder, position: Int) {
        val setNoteList = items[position].noteSetList
        val setAdapter = SetRecordsAdapter().apply {
            items = setNoteList.toMutableList()
        }
        holder.bind(items[position])
        holder.bindAdapter(setAdapter)
        holder.setExerciseNumber(position + 1)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
