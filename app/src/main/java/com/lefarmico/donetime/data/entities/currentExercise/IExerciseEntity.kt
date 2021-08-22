package com.lefarmico.donetime.data.entities.currentExercise

interface IExerciseEntity {
    val name: String
    val tag: String
    var exerciseSetList: ExerciseSetList
}
