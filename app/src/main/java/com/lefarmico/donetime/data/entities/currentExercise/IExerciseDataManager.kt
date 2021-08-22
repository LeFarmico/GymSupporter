package com.lefarmico.donetime.data.entities.currentExercise

interface IExerciseDataManager {
    val date: String
    var exercises: MutableList<ExerciseData>
}
