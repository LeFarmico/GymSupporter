package com.lefarmico.data.utils

import com.lefarmico.domain.entity.WorkoutRecordsDto

fun List<WorkoutRecordsDto.ExerciseWithSets>.setWorkoutId(workoutId: Int) = this.map {
    WorkoutRecordsDto.ExerciseWithSets(
        exercise = WorkoutRecordsDto.Exercise(
            workoutId = workoutId,
            exerciseName = it.exercise.exerciseName
        ),
        setList = it.setList
    )
}

fun List<WorkoutRecordsDto.Set>.setExerciseId(exerciseId: Int) = this.map {
    WorkoutRecordsDto.Set(
        exerciseId = exerciseId,
        setNumber = it.setNumber,
        weight = it.weight,
        reps = it.reps,
        measureType = WorkoutRecordsDto.MeasureType.KILO
    )
}
