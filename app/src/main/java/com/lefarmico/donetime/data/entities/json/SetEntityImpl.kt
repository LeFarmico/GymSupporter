package com.lefarmico.donetime.data.entities.json

import com.google.gson.annotations.Expose
import com.lefarmico.donetime.adapters.viewHolders.factories.ExerciseViewHolderFactory
import com.lefarmico.donetime.data.entities.exercise.ISetEntity
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

class SetEntityImpl(setEntity: ISetEntity) : ISetEntity {
    @Expose
    override var setNumber: Int = setEntity.setNumber
    @Expose
    override val weights: Float = setEntity.weights
    @Expose
    override val reps: Int = setEntity.reps
    override val type: IViewHolderFactory<ItemType> = ExerciseViewHolderFactory.SET
}
