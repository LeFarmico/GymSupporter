package com.lefarmico.donetime.data.entities.exercise

import com.lefarmico.donetime.data.models.ICurrentExerciseSetItem

interface IExerciseDataManager : IWorkoutEntity {
    override var exercises: MutableList<IExerciseData>
    var buttonEventAddSet: (() -> ICurrentExerciseSetItem)
    var buttonEventDelSet: ((IExerciseData) -> Unit)
}
