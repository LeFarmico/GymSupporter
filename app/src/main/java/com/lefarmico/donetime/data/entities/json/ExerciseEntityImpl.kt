package com.lefarmico.donetime.data.entities.json

import com.google.gson.annotations.Expose
import com.lefarmico.donetime.data.entities.exercise.IExerciseEntity
import com.lefarmico.donetime.data.models.ICurrentExerciseSetItem

class ExerciseEntityImpl(exerciseData: IExerciseEntity) : IExerciseEntity {
    @Expose
    override val name: String = exerciseData.name
    @Expose
    override val tag: String = exerciseData.tag
    @Expose
    override val sets: List<ICurrentExerciseSetItem> = exerciseData.sets.map { SetEntityImpl(it) }
}
