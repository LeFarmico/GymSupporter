package com.lefarmico.donetime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.donetime.data.entities.note.NoteWorkout
import com.lefarmico.donetime.databinding.ItemNoteWorkoutBinding

class WorkoutNoteAdapter : RecyclerView.Adapter<WorkoutNoteAdapter.WorkoutNoteViewHolder>() {

    var items = mutableListOf<NoteWorkout>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class WorkoutNoteViewHolder(
        itemNoteWorkoutBinding: ItemNoteWorkoutBinding
    ) : RecyclerView.ViewHolder(itemNoteWorkoutBinding.root) {

        private val date = itemNoteWorkoutBinding.workoutDate
        private val exerciseNoteRecycler = itemNoteWorkoutBinding.exercisesRecycler

        fun bind(noteWorkout: NoteWorkout) {
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
        adapter.setExercise(items[position].noteExerciseList)
        holder.bind(items[position])
        holder.bindAdapter(adapter)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
