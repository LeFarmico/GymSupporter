package com.lefarmico.donetime.data.entities.json

import com.google.gson.annotations.Expose
import com.lefarmico.donetime.data.entities.exercise.IExerciseEntity
import com.lefarmico.donetime.data.entities.workout.IWorkoutEntity

class WorkoutEntityImpl(workoutData: IWorkoutEntity) : IWorkoutEntity {
    @Expose
    override val date: String = workoutData.date
    @Expose
    override val exercises: List<IExerciseEntity> = workoutData
        .exercises
        .map { ExerciseEntityImpl(it) }
}
