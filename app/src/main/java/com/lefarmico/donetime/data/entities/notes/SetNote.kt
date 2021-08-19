package com.lefarmico.donetime.data.entities.notes

import com.lefarmico.donetime.adapters.viewHolders.factories.NoteExerciseViewHolderFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

data class SetNote(
    val setNumber: Int,
    val weight: Float,
    val reps: Int
) : ItemType {
    override val type: IViewHolderFactory<ItemType> = NoteExerciseViewHolderFactory.SET
}
