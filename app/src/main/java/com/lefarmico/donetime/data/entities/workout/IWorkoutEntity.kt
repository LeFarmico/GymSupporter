package com.lefarmico.donetime.data.entities.workout

import com.lefarmico.donetime.data.entities.exercise.IExerciseEntity

interface IWorkoutEntity {
    val date: String
    val exercises: List<IExerciseEntity>
}
