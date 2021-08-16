package com.lefarmico.donetime.data.entities.workout

import com.lefarmico.donetime.data.entities.exercise.IExerciseData
import com.lefarmico.donetime.data.entities.exercise.ISetEntity

interface IWorkoutData : IWorkoutEntity {
    override var exercises: MutableList<IExerciseData>
    var buttonEventAddSet: (() -> ISetEntity)
    var buttonEventDelSet: ((IExerciseData) -> Unit)
}
