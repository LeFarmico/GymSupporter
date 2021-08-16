package com.lefarmico.donetime.data.entities.workout

import com.lefarmico.donetime.data.entities.workout.exercise.IExerciseEntity

interface IWorkoutEntity {
    val date: String
    val exercises: List<IExerciseEntity>
}
