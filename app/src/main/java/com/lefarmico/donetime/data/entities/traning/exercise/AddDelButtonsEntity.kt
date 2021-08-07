package com.lefarmico.donetime.data.entities.traning.exercise

import com.lefarmico.donetime.adapters.viewHolders.ExerciseViewHolderFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

data class AddDelButtonsEntity(
    val addButtonCallback: () -> Unit,
    val deleteButtonCallback: () -> Unit,
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = ExerciseViewHolderFactory.BUTTONS
}
