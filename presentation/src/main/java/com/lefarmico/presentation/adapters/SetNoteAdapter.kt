package com.lefarmico.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.presentation.databinding.ItemExerciseSetBinding

class SetNoteAdapter : RecyclerView.Adapter<SetNoteAdapter.SetViewHolder>() {

    class SetViewHolder(
        itemExerciseSetBinding: ItemExerciseSetBinding
    ) : RecyclerView.ViewHolder(itemExerciseSetBinding.root) {

        private var reps = itemExerciseSetBinding.reps
        private var setNumber = itemExerciseSetBinding.setNumber
        private var wieght = itemExerciseSetBinding.weight

        fun bind(noteSet: WorkoutRecordsDto.Set) {
            reps.text = noteSet.reps.toString()
            wieght.text = noteSet.weight.toString()
        }
        fun bindSetNumber(number: Int) {
            setNumber.text = number.toString()
        }
    }
    var items = mutableListOf<WorkoutRecordsDto.Set>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        return SetViewHolder(
            ItemExerciseSetBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.bind(items[position])
        holder.bindSetNumber(position + 1)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
