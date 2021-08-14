package com.lefarmico.donetime.data.entities.workout

import com.lefarmico.donetime.data.entities.workout.exercise.IExerciseData
import com.lefarmico.donetime.data.entities.workout.exercise.ISetEntity

interface IWorkoutData {
    var exercises: MutableList<IExerciseData>
    var buttonEventAddSet: (() -> ISetEntity)
    var buttonEventDelSet: ((IExerciseData) -> Unit)
}
