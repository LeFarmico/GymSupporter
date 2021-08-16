package com.lefarmico.donetime.data.entities.workout.exercise

interface IExerciseEntity {
    val name: String
    val tag: String
    val sets: List<ISetEntity>
}
