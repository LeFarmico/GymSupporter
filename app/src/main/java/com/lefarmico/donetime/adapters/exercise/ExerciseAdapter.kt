package com.lefarmico.donetime.adapters.exercise

import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.exercise.entity.AddDelButtons
import com.lefarmico.donetime.adapters.exercise.entity.Exercise
import com.lefarmico.donetime.adapters.exercise.entity.ExerciseSet
import com.lefarmico.donetime.adapters.viewHolders.ExerciseSetViewHolder
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerAdapter
import com.lefarmico.lerecycle.LeRecyclerViewHolder
import java.lang.IndexOutOfBoundsException

class ExerciseAdapter : LeRecyclerAdapter() {

    private val exercises = mutableListOf<Exercise>()

    override fun onBindViewHolder(
        holder: LeRecyclerViewHolder<ItemType>,
        position: Int
    ) {
        holder.bind(items[position], position, items.size)
        val item = items[position]
        bindRoundCorners(item, position, holder)
    }

    private fun bindRoundCorners(item: ItemType, position: Int, holder: LeRecyclerViewHolder<ItemType>) {
        if (item is ExerciseSet) {
            val isPrevExercise: Boolean = try { items[position - 1] is ExerciseSet } catch (e: IndexOutOfBoundsException) { false }
            val isNextExercise: Boolean = try { items[position + 1] is ExerciseSet } catch (e: IndexOutOfBoundsException) { false }
            val viewHolder = (holder as ExerciseSetViewHolder)

            // TODO упростить
            if (!isPrevExercise) {
                if (isNextExercise) {
                    viewHolder.bindBackGround(R.drawable.top_corners_shape)
                } else {
                    viewHolder.bindBackGround(R.drawable.single_corners_shape)
                }
            } else {
                if (isNextExercise) {
                    viewHolder.bindBackGround(R.drawable.middle_corners_shape)
                } else {
                    viewHolder.bindBackGround(R.drawable.bottom_corners_shape)
                }
            }
        }
    }

    fun addExercise(exercise: Exercise) {
        exercises.add(exercise)
    }

    fun setItems() {
        val itemsList = mutableListOf<ItemType>()
        for (i in exercises.indices) {
            val exercisesItems = exercises[i].getItems()
            itemsList.addAll(exercisesItems)
        }
        items = itemsList
    }
}
