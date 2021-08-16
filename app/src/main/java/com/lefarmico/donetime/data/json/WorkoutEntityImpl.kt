package com.lefarmico.donetime.data.json

import com.google.gson.annotations.Expose
import com.lefarmico.donetime.data.entities.workout.IWorkoutEntity
import com.lefarmico.donetime.data.entities.workout.exercise.IExerciseEntity

class WorkoutEntityImpl(workoutData: IWorkoutEntity) : IWorkoutEntity {
    @Expose
    override val date: String = workoutData.date
    @Expose
    override val exercises: List<IExerciseEntity> = workoutData
        .exercises
        .map { ExerciseEntityImpl(it) }
}
