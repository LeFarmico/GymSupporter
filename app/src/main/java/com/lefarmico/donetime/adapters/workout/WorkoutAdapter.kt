package com.lefarmico.donetime.adapters.workout

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.donetime.adapters.exercise.ExerciseAdapter
import com.lefarmico.donetime.databinding.ItemExerciseFullBinding

class WorkoutAdapter : RecyclerView.Adapter<WorkoutAdapter.ViewHolder>() {

    private val exercises = mutableListOf<ExerciseAdapter>()
    private var isActivePosition: Int = -1

    class ViewHolder(
        itemExerciseFullBinding: ItemExerciseFullBinding
    ) : RecyclerView.ViewHolder(
        itemExerciseFullBinding.root
    ) {
        private val recycler = itemExerciseFullBinding.exerciseItem

        fun bind(adapter: ExerciseAdapter) {
            recycler.adapter = adapter
        }
        fun bindAsActive(adapter: ExerciseAdapter) {
            adapter.setActive(true)
        }
        fun bindAsNotActive(adapter: ExerciseAdapter) {
            adapter.setActive(false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemExerciseFullBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(exercises[position])
        if (position == isActivePosition) {
            holder.bindAsActive(exercises[position])
            exercises[position].setOnClickEvent {
                onActiveExerciseCallback(position)
            }
        } else {
            holder.bindAsNotActive(exercises[position])
            exercises[position].setOnClickEvent {
                onNotActiveExerciseCallback(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun addExercise(exerciseAdapter: ExerciseAdapter) {
        exercises.add(exerciseAdapter)
        notifyDataSetChanged()
    }

    fun deleteExercise(exerciseAdapter: ExerciseAdapter) {
        if (exerciseAdapter.getSetCount() == 0) {
            exercises.remove(exerciseAdapter)
        }
    }

    private fun onActiveExerciseCallback(position: Int) {
        Log.d("LOLENESS", "FOO")
    }

    private fun onNotActiveExerciseCallback(position: Int) {
        isActivePosition = position
        notifyDataSetChanged()
    }
}
