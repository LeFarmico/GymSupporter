package com.lefarmico.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.presentation.adapters.diffUtil.WorkoutRecordsDiffCallback
import com.lefarmico.presentation.databinding.ItemNoteWorkoutBinding

class WorkoutRecordsAdapter : RecyclerView.Adapter<WorkoutRecordsAdapter.WorkoutNoteViewHolder>() {

    var items = listOf<WorkoutRecordsDto.Workout>()
        set(value) {
            val oldField = field
            field = value
            val diffCallback = WorkoutRecordsDiffCallback(oldField, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    class WorkoutNoteViewHolder(
        itemNoteWorkoutBinding: ItemNoteWorkoutBinding
    ) : RecyclerView.ViewHolder(itemNoteWorkoutBinding.root) {

        private val date = itemNoteWorkoutBinding.workoutDate
        private val exerciseNoteRecycler = itemNoteWorkoutBinding.exercisesRecycler

        fun bind(noteWorkout: WorkoutRecordsDto.Workout) {
            date.text = noteWorkout.date
        }
        fun bindAdapter(adapter: ExerciseRecordsAdapter) {
            exerciseNoteRecycler.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutNoteViewHolder {
        return WorkoutNoteViewHolder(
            ItemNoteWorkoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WorkoutNoteViewHolder, position: Int) {
        val adapter = ExerciseRecordsAdapter()
        adapter.setExercise(items[position].exerciseList)
        holder.bind(items[position])
        holder.bindAdapter(adapter)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
