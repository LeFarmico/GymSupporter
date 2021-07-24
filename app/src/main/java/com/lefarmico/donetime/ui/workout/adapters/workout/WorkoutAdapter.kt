package com.lefarmico.donetime.ui.workout.adapters.workout

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.donetime.databinding.ItemExerciseFullBinding
import com.lefarmico.donetime.ui.workout.adapters.AdapterBuilder
import com.lefarmico.donetime.ui.workout.data.Exercise
import com.lefarmico.donetime.ui.workout.data.WorkoutRepository
import com.lefarmico.donetime.ui.workout.viewHolders.ExerciseViewHolder

class WorkoutAdapter(
    private val workoutRepo: WorkoutRepository
) : RecyclerView.Adapter<ExerciseViewHolder>() {

    companion object {
        val EXERCISE = 1
        val ADDEXERCIE = 2
        val STARTWORKOUT = 3
    }
    private val adapterBuilder = AdapterBuilder()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ItemExerciseFullBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = workoutRepo.getExercise(position)
        val adapter = adapterBuilder.createExerciseAdapter(
            exercise,
            workoutRepo.isActivePosition(position)
        )

        // TODO: Вынести в метод для внешнего доступа
        adapter.setAddButtonEvent {
            exercise.addSet(500f, 900)
            notifyDataSetChanged()
        }
        adapter.setDelButtonEvent {
            exercise.delSet()
            if (exercise.getSetCount() == 0) {
                deleteExercise(position)
            }
            notifyDataSetChanged()
        }
        holder.recycler.adapter = adapter
        if (workoutRepo.isActivePosition(position)) {
            adapter.setOnClickEvent {
                onActiveExerciseCallback(position)
            }
        } else {
            adapter.setOnClickEvent {
                onNotActiveExerciseCallback(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return workoutRepo.getExercisesSize()
    }

    fun addExercise(exercise: Exercise) {
        workoutRepo.addExercise(exercise)
        notifyDataSetChanged()
    }

    fun deleteExercise(position: Int) {
        workoutRepo.deleteExercise(position)
    }

    private fun onActiveExerciseCallback(position: Int) {
        Log.d("LOLENESS", "FOO")
    }

    private fun onNotActiveExerciseCallback(position: Int) {
        workoutRepo.setActivePosition(position)
        notifyDataSetChanged()
    }
}
