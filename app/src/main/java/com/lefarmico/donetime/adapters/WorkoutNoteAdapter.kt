package com.lefarmico.donetime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.donetime.data.entities.note.WorkoutNote
import com.lefarmico.donetime.databinding.ItemWorkoutNoteBinding

class WorkoutNoteAdapter : RecyclerView.Adapter<WorkoutNoteAdapter.WorkoutNoteViewHolder>() {

    var items = mutableListOf<WorkoutNote>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class WorkoutNoteViewHolder(
        itemWorkoutNoteBinding: ItemWorkoutNoteBinding
    ) : RecyclerView.ViewHolder(itemWorkoutNoteBinding.root) {

        private val date = itemWorkoutNoteBinding.workoutDate
        private val exerciseNoteRecycler = itemWorkoutNoteBinding.exercisesRecycler

        fun bind(exerciseNote: WorkoutNote) {
            date.text = exerciseNote.date
        }
        fun bindAdapter(adapter: ExerciseNoteAdapter) {
            exerciseNoteRecycler.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutNoteViewHolder {
        return WorkoutNoteViewHolder(
            ItemWorkoutNoteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WorkoutNoteViewHolder, position: Int) {
        val adapter = ExerciseNoteAdapter()
        adapter.setExercise(items[position].exerciseNoteList)
        holder.bind(items[position])
        holder.bindAdapter(adapter)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
