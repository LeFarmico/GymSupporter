package com.lefarmico.donetime.data.entities.workout.exercise

import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

interface ISetEntity : ItemType {
    var setNumber: Int
    val weights: Float
    val reps: Int
    override val type: IViewHolderFactory<ItemType>
}
