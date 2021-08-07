package com.lefarmico.donetime.data.entities.traning

import com.lefarmico.donetime.adapters.viewHolders.WorkoutViewHolderFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

class AddExerciseEntity(
    val addButtonCallback: () -> Unit
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = WorkoutViewHolderFactory.ADD_EXERCISE
}
