package com.lefarmico.donetime.data.entities.currentExercise

interface IWorkoutEntity {
    val date: String
    val exercises: List<IExerciseEntity>
}
