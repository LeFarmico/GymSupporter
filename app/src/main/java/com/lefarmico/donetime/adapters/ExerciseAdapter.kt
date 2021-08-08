package com.lefarmico.donetime.adapters

import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.viewHolders.ExerciseSetViewHolder
import com.lefarmico.donetime.adapters.viewHolders.factories.ExerciseViewHolderFactory
import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseMuscleSetEntity
import com.lefarmico.donetime.utils.ItemObservable
import com.lefarmico.donetime.utils.ItemObserver
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerAdapter
import com.lefarmico.lerecycle.LeRecyclerViewHolder
import com.lefarmico.lerecycle.extractValues

class ExerciseAdapter(
    dataSource: ItemObservable
) : LeRecyclerAdapter(), ItemObserver {

    init {
        setItemTypes(
            extractValues<ExerciseViewHolderFactory>()
        )
        dataSource.registerObserver(this)
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
        if (item is ExerciseMuscleSetEntity) {
            val isPrevExercise: Boolean = try {
                items[position - 1] is ExerciseMuscleSetEntity
            } catch (e: IndexOutOfBoundsException) { false }
            val isNextExercise: Boolean = try {
                items[position + 1] is ExerciseMuscleSetEntity
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

    override fun updateData(items: MutableList<ItemType>) {
        this.items = items
    }
}
