package com.lefarmico.donetime.ui.workout.adapters.exercise

import com.lefarmico.donetime.R
import com.lefarmico.donetime.ui.workout.data.Exercise
import com.lefarmico.donetime.ui.workout.data.ExerciseSet
import com.lefarmico.donetime.ui.workout.viewHolders.ExerciseSetViewHolder
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerAdapter
import com.lefarmico.lerecycle.LeRecyclerViewHolder
import com.lefarmico.lerecycle.extractValues
import java.lang.IndexOutOfBoundsException

class ExerciseAdapter : LeRecyclerAdapter() {

    private lateinit var exercise: Exercise

    init {
        setItemTypes(
            extractValues<ExerciseMenuFactory>()
        )
    }

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
            val isPrevExercise: Boolean = try {
                items[position - 1] is ExerciseSet
            } catch (e: IndexOutOfBoundsException) { false }
            val isNextExercise: Boolean = try {
                items[position + 1] is ExerciseSet
            } catch (e: IndexOutOfBoundsException) { false }
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

    fun setExercise(ex: Exercise) {
        exercise = ex
        val exerciseItems = ex.getItems()
        items = exerciseItems
    }

    fun setActive(isActive: Boolean) {
        exercise.isActive = isActive
        items = exercise.getItems()
    }

    fun setAddButtonEvent(addEvent: () -> Unit) {
        exercise.addButtonEvent = addEvent
    }
    fun setDelButtonEvent(delEvent: () -> Unit) {
        exercise.delButtonEvent = delEvent
    }
}
