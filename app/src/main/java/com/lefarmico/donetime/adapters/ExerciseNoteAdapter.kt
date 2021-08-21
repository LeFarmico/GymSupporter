package com.lefarmico.donetime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.donetime.data.entities.note.ExerciseNote
import com.lefarmico.donetime.databinding.ItemNoteBinding

class ExerciseNoteAdapter : RecyclerView.Adapter<ExerciseNoteAdapter.WorkoutNoteViewHolder>() {

    var items = mutableListOf<ExerciseNote>()
    
    class WorkoutNoteViewHolder(
        itemNoteBinding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(itemNoteBinding.root) {

        private var exercise = itemNoteBinding.exerciseName
        private var exerciseNumber = itemNoteBinding.exerciseNumber
        private val setList = itemNoteBinding.setsList

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
            ItemNoteBinding.inflate(
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
