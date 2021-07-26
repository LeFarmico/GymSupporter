package com.lefarmico.donetime.ui.workout.adapters.workout

import android.util.Log
import com.lefarmico.donetime.ui.workout.adapters.AdapterBuilder
import com.lefarmico.donetime.ui.workout.data.AddExercise
import com.lefarmico.donetime.ui.workout.data.Exercise
import com.lefarmico.donetime.ui.workout.data.Workout
import com.lefarmico.donetime.ui.workout.viewHolders.ExerciseViewHolder
import com.lefarmico.donetime.ui.workout.viewHolders.WorkoutAddExViewHolder
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerAdapter
import com.lefarmico.lerecycle.LeRecyclerViewHolder
import com.lefarmico.lerecycle.extractValues

class WorkoutAdapter(
    private val workoutRepo: Workout
) : LeRecyclerAdapter() {

    init {
        setItemTypes(
            extractValues<WorkoutViewHolderFactory>()
        )
        items = workoutRepo.getItems()
    }
    private val adapterBuilder = AdapterBuilder()

    override fun onBindViewHolder(holder: LeRecyclerViewHolder<ItemType>, position: Int) {
        super.onBindViewHolder(holder, position)
        when (items[position]) {
            is Exercise -> {
                holder as ExerciseViewHolder
                bindExerciseItem(holder, position)
            }
            is AddExercise -> {
                holder as WorkoutAddExViewHolder
                val exercise = Exercise().apply {
                    addSet(100f, 951)
                    addSet(100f, 951)
                    setNameAndTags("Push upps", "Back")
                }
                setAddExerciseEvent {
                    addExercise(exercise)
                }
                holder.bind(items[position], position, itemCount)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addExercise(exercise: Exercise) {
        workoutRepo.addExercise(exercise)
        items = workoutRepo.getItems()
        notifyDataSetChanged()
    }

    fun deleteExercise(position: Int) {
        workoutRepo.deleteExercise(position)
        items = workoutRepo.getItems()
    }

    private fun onActiveExerciseCallback(position: Int) {
        Log.d("LOLENESS", "FOO")
    }

    private fun onNotActiveExerciseCallback(position: Int) {
        workoutRepo.setActivePosition(position)
        notifyDataSetChanged()
    }

    private fun bindExerciseItem(holder: ExerciseViewHolder, position: Int) {
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
        if (workoutRepo.isActivePosition(position)) {
            adapter.setOnClickEvent {
                onActiveExerciseCallback(position)
            }
        } else {
            adapter.setOnClickEvent {
                onNotActiveExerciseCallback(position)
            }
        }
        holder.bindAdapter(adapter)
    }

    fun setAddExerciseEvent(addExEvent: () -> Unit) {
        workoutRepo.addExButtonEvent = {
            addExEvent()
        }
    }
}
