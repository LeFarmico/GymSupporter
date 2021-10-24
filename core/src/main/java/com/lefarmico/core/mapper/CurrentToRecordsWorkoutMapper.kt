package com.lefarmico.core.mapper

import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.entity.WorkoutRecordsDto

fun CurrentWorkoutDto.Set.toRecords(exerciseId: Int) = WorkoutRecordsDto.Set(
    id = id,
    exerciseId = exerciseId,
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = WorkoutRecordsDto.MeasureType.KILO
)

fun CurrentWorkoutDto.Exercise.toRecords(workoutId: Int) = WorkoutRecordsDto.Exercise(
    id = id, workoutId = workoutId, exerciseName = title
)

fun List<CurrentWorkoutDto.ExerciseWithSets>.toRecords() = this.map {
    WorkoutRecordsDto.ExerciseWithSets(
        exercise = WorkoutRecordsDto.Exercise(
            id = it.exercise.id,
            exerciseName = it.exercise.title
        ),
        setList = it.setList.map { set ->
            WorkoutRecordsDto.Set(
                setNumber = set.setNumber,
                weight = set.weight,
                reps = set.reps,
                measureType = WorkoutRecordsDto.MeasureType.KILO
            )
        }

    )
}
