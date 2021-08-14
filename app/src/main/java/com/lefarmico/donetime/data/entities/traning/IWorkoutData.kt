package com.lefarmico.donetime.data.entities.traning

import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseData
import com.lefarmico.donetime.data.entities.traning.exercise.ISetEntity

interface IWorkoutData {
    var exercises: MutableList<ExerciseData>
    var buttonEventAddSet: (() -> ISetEntity)
    var buttonEventDelSet: ((ExerciseData) -> Unit)
}
