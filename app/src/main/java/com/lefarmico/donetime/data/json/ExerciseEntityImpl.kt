package com.lefarmico.donetime.data.json

import com.google.gson.annotations.Expose
import com.lefarmico.donetime.data.entities.workout.exercise.IExerciseEntity
import com.lefarmico.donetime.data.entities.workout.exercise.ISetEntity

class ExerciseEntityImpl(exerciseData: IExerciseEntity) : IExerciseEntity {
    @Expose
    override val name: String = exerciseData.name
    @Expose
    override val tag: String = exerciseData.tag
    @Expose
    override val sets: List<ISetEntity> = exerciseData.sets.map { SetEntityImpl(it) }
}
