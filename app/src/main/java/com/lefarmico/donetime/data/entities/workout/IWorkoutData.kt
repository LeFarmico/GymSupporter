package com.lefarmico.donetime.data.entities.workout

import com.lefarmico.donetime.data.entities.exercise.IExerciseData
import com.lefarmico.donetime.data.models.ICurrentExerciseSetItem

interface IWorkoutData : IWorkoutEntity {
    override var exercises: MutableList<IExerciseData>
    var buttonEventAddSet: (() -> ICurrentExerciseSetItem)
    var buttonEventDelSet: ((IExerciseData) -> Unit)
}
