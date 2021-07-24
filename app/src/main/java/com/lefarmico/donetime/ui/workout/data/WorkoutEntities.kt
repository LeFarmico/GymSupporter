package com.lefarmico.donetime.ui.workout.data

import com.lefarmico.donetime.ui.workout.adapters.workout.WorkoutViewHolderFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

class AddExercise(
    val addButtonCallback: () -> Unit
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = WorkoutViewHolderFactory.ADD_EXERCISE
}
