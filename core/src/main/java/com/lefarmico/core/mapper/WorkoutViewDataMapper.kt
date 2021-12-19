package com.lefarmico.core.mapper

import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.domain.entity.WorkoutRecordsDto
import java.lang.IllegalArgumentException

fun WorkoutRecordsViewData.Exercise.toDto() = WorkoutRecordsDto.Exercise(
    id = id,
    exerciseName = exerciseName,
    workoutId = workoutId
)

fun WorkoutRecordsViewData.Set.toDto() = WorkoutRecordsDto.Set(
    id = id,
    exerciseId = exerciseId,
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = measureType.toDto()
)

fun WorkoutRecordsViewData.Workout.toDto() = WorkoutRecordsDto.Workout(
    id = id,
    date = date,
    title = title
)

fun List<WorkoutRecordsViewData.Set>.toDtoSet() = this.map { it.toDto() }

fun List<WorkoutRecordsViewData.Exercise>.toDtoExercise() = this.map { it.toDto() }

fun List<WorkoutRecordsViewData.Workout>.toDtoWorkout() = this.map { it.toDto() }

fun List<WorkoutRecordsViewData.ExerciseWithSets>.toDtoExWithSets() = this.map { it.toDto() }

fun List<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>.toDtoWorkoutWithExAndSets() =
    this.map { it.toDto() }

fun WorkoutRecordsViewData.MeasureType.toDto(): WorkoutRecordsDto.MeasureType {
    return when (this.typeNumber) {
        1 -> WorkoutRecordsDto.MeasureType.KILO
        2 -> WorkoutRecordsDto.MeasureType.LB
        else -> throw (IllegalArgumentException())
    }
}

fun WorkoutRecordsViewData.WorkoutWithExercisesAndSets.toDto() = WorkoutRecordsDto.WorkoutWithExercisesAndSets(
    workout = workout.toDto(),
    exerciseWithSetsList = exerciseWithSetsList.toDtoExWithSets()
)

fun WorkoutRecordsViewData.ExerciseWithSets.toDto() = WorkoutRecordsDto.ExerciseWithSets(
    exercise = exercise.toDto(),
    setList = setList.toDtoSet()
)
