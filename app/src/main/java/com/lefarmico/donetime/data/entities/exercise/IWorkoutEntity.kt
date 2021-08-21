package com.lefarmico.donetime.data.entities.exercise

interface IWorkoutEntity {
    val date: String
    val exercises: List<IExerciseEntity>
}
