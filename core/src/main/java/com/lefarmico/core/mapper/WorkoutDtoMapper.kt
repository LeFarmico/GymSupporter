package com.lefarmico.core.mapper

import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.domain.entity.WorkoutRecordsDto
import java.lang.IllegalArgumentException

fun WorkoutRecordsDto.Exercise.toViewData() = WorkoutRecordsViewData.Exercise(
    id = id,
    exerciseName = exerciseName,
    workoutId = workoutId
)

fun WorkoutRecordsDto.Set.toViewData() = WorkoutRecordsViewData.Set(
    id = id,
    exerciseId = exerciseId,
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = measureType.toViewData()
)

fun WorkoutRecordsDto.Workout.toViewData() = WorkoutRecordsViewData.Workout(
    id = id,
    date = date
)

fun List<WorkoutRecordsDto.Set>.toSetListViewData() = this.map { it.toViewData() }

fun List<WorkoutRecordsDto.Exercise>.toExerciseListViewData() = this.map { it.toViewData() }

fun List<WorkoutRecordsDto.Workout>.toWorkoutListViewData() = this.map { it.toViewData() }

fun List<WorkoutRecordsDto.ExerciseWithSets>.toExerciseWithSetsListViewData() = this.map { it.toViewData() }

fun List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>.toWorkoutWithExercisesAndSetsViewData() =
    this.map { it.toViewData() }

fun WorkoutRecordsDto.MeasureType.toViewData(): WorkoutRecordsViewData.MeasureType {
    return when (this.typeNumber) {
        1 -> WorkoutRecordsViewData.MeasureType.KILO
        2 -> WorkoutRecordsViewData.MeasureType.LB
        else -> throw (IllegalArgumentException())
    }
}

fun WorkoutRecordsDto.WorkoutWithExercisesAndSets.toViewData() = WorkoutRecordsViewData.WorkoutWithExercisesAndSets(
    workout = workout.toViewData(),
    exerciseWithSetsList = exerciseWithSetsList.toExerciseWithSetsListViewData()
)

fun WorkoutRecordsDto.ExerciseWithSets.toViewData() = WorkoutRecordsViewData.ExerciseWithSets(
    exercise = exercise.toViewData(),
    setList = setList.toSetListViewData()
)
