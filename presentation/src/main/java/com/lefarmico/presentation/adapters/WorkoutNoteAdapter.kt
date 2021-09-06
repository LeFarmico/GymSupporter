package com.lefarmico.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.presentation.databinding.ItemNoteWorkoutBinding

class WorkoutNoteAdapter : RecyclerView.Adapter<WorkoutNoteAdapter.WorkoutNoteViewHolder>() {

    var items = listOf<WorkoutRecordsDto.Workout>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class WorkoutNoteViewHolder(
        itemNoteWorkoutBinding: ItemNoteWorkoutBinding
    ) : RecyclerView.ViewHolder(itemNoteWorkoutBinding.root) {

        private val date = itemNoteWorkoutBinding.workoutDate
        private val exerciseNoteRecycler = itemNoteWorkoutBinding.exercisesRecycler

        fun bind(noteWorkout: WorkoutRecordsDto.Workout) {
            date.text = noteWorkout.date
        }
        fun bindAdapter(adapter: ExerciseNoteAdapter) {
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
        val adapter = ExerciseNoteAdapter()
        adapter.setExercise(items[position].exerciseList)
        holder.bind(items[position])
        holder.bindAdapter(adapter)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
