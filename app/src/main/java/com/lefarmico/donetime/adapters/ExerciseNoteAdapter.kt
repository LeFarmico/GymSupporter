package com.lefarmico.donetime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.donetime.data.entities.note.ExerciseNote
import com.lefarmico.donetime.databinding.ItemNoteExerciseBinding

class ExerciseNoteAdapter : RecyclerView.Adapter<ExerciseNoteAdapter.WorkoutNoteViewHolder>() {

    var items = mutableListOf<ExerciseNote>()
    
    class WorkoutNoteViewHolder(
        itemNoteExerciseBinding: ItemNoteExerciseBinding
    ) : RecyclerView.ViewHolder(itemNoteExerciseBinding.root) {

        private var exercise = itemNoteExerciseBinding.exerciseName
        private var exerciseNumber = itemNoteExerciseBinding.exerciseNumber
        private val setList = itemNoteExerciseBinding.setsList

        fun bind(exerciseNote: ExerciseNote) {
            exercise.text = exerciseNote.exerciseName
        }
        fun bindAdapter(adapter: SetNoteAdapter) {
            setList.adapter = adapter
        }
        fun setExerciseNumber(number: Int) {
            exerciseNumber.text = "$number. "
        }
    }

    fun setExercise(exerciseList: MutableList<ExerciseNote>) {
        items = exerciseList
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutNoteViewHolder {
        return WorkoutNoteViewHolder(
            ItemNoteExerciseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WorkoutNoteViewHolder, position: Int) {
        val setNoteList = items[position].setNoteList
        val setAdapter = SetNoteAdapter().apply {
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
