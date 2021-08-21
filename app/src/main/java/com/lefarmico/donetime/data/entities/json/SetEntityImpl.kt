package com.lefarmico.donetime.data.entities.json

import com.google.gson.annotations.Expose
import com.lefarmico.donetime.data.models.ICurrentExerciseSetItem

class SetEntityImpl(setEntity: ICurrentExerciseSetItem) : ICurrentExerciseSetItem {
    @Expose
    override var setNumber: Int = setEntity.setNumber
    @Expose
    override val weights: Float = setEntity.weights
    @Expose
    override val reps: Int = setEntity.reps
}
